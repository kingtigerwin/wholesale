#!/usr/bin/env bash
# Deletes everything infra/deploy.sh created, then checks that nothing billable is left behind.
#
# Usage: infra/destroy.sh            (needs AWS credentials and gh)
set -euo pipefail

REGION=ap-southeast-2
STACK=wholesale
DOMAIN=api.haoran.cloud
HOSTED_ZONE_ID=Z5UXKBB5IU578

export AWS_REGION=$REGION

echo "==> Removing GitHub Actions variables (so master pushes skip the deploy job again)"
gh variable delete AWS_ROLE_ARN 2>/dev/null || true
gh variable delete EXPRESS_SERVICE_ARN 2>/dev/null || true

# Remember the certificate's validation record so it can be removed if CloudFormation leaves it in the zone.
CERTIFICATE_ARN=$(aws cloudformation describe-stack-resource --stack-name "$STACK" --logical-resource-id Certificate \
    --query 'StackResourceDetail.PhysicalResourceId' --output text 2>/dev/null || true)
VALIDATION_RECORD=""
if [[ -n $CERTIFICATE_ARN && $CERTIFICATE_ARN != None ]]; then
    VALIDATION_RECORD=$(aws acm describe-certificate --certificate-arn "$CERTIFICATE_ARN" \
        --query 'Certificate.DomainValidationOptions[0].ResourceRecord' --output json)
fi

echo "==> Deleting stack $STACK (Express service, load balancer and RDS take several minutes)"
aws cloudformation delete-stack --stack-name "$STACK"
aws cloudformation wait stack-delete-complete --stack-name "$STACK"

if [[ -n $VALIDATION_RECORD && $VALIDATION_RECORD != null ]]; then
    NAME=$(jq -r .Name <<<"$VALIDATION_RECORD")
    EXISTING=$(aws route53 list-resource-record-sets --hosted-zone-id "$HOSTED_ZONE_ID" \
        --query "ResourceRecordSets[?Name=='$NAME' && Type=='CNAME']" --output json)
    if [[ $EXISTING != "[]" ]]; then
        echo "==> Removing leftover certificate validation record $NAME"
        jq '{Changes: [{Action: "DELETE", ResourceRecordSet: .[0]}]}' <<<"$EXISTING" > /tmp/wholesale-dns-delete.json
        aws route53 change-resource-record-sets --hosted-zone-id "$HOSTED_ZONE_ID" \
            --change-batch file:///tmp/wholesale-dns-delete.json --query 'ChangeInfo.Status' --output text
    fi
fi

echo "==> Checking for leftovers (each line should be empty)"
echo "ECR repository:      $(aws ecr describe-repositories --query "repositories[?repositoryName=='wholesale'].repositoryName" --output text)"
echo "RDS instance:        $(aws rds describe-db-instances --query "DBInstances[?DBInstanceIdentifier=='wholesale'].DBInstanceStatus" --output text)"
echo "RDS snapshots:       $(aws rds describe-db-snapshots --query "DBSnapshots[?contains(DBSnapshotIdentifier,'wholesale')].DBSnapshotIdentifier" --output text)"
echo "ECS service:         $(aws ecs describe-services --cluster wholesale --services wholesale --query "services[?status!='INACTIVE'].status" --output text 2>/dev/null)"
echo "Load balancers:      $(aws elbv2 describe-load-balancers --query "LoadBalancers[?contains(LoadBalancerName,'ecs-express')].LoadBalancerName" --output text)"
echo "Log group:           $(aws logs describe-log-groups --log-group-name-prefix /ecs/wholesale --query 'logGroups[].logGroupName' --output text)"
echo "Secrets (live):      $(aws secretsmanager list-secrets --query "SecretList[?contains(Name,'wholesale') && DeletedDate==null].Name" --output text)"
echo "DNS record $DOMAIN: $(aws route53 list-resource-record-sets --hosted-zone-id "$HOSTED_ZONE_ID" \
    --query "ResourceRecordSets[?Name=='$DOMAIN.'].Type" --output text)"
