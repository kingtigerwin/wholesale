#!/usr/bin/env bash
# Creates (or updates) the wholesale stack in three phases:
#   1. base resources: ECR, RDS, secrets, IAM roles, certificate, cluster
#   2. push an image built from this checkout and create the Express service
#   3. route api.haoran.cloud to the Express-managed load balancer
# Afterwards GitHub Actions deploys new images on every push to master.
#
# Usage: infra/deploy.sh            (needs AWS credentials, Docker and gh)
set -euo pipefail

REGION=ap-southeast-2
STACK=wholesale
DOMAIN=api.haoran.cloud
cd "$(dirname "$0")/.."

export AWS_REGION=$REGION

deploy() {
    aws cloudformation deploy \
        --stack-name "$STACK" \
        --template-file infra/wholesale.yaml \
        --capabilities CAPABILITY_NAMED_IAM \
        --no-fail-on-empty-changeset \
        --parameter-overrides "VpcId=$VPC_ID" "$@"
}

output() {
    aws cloudformation describe-stacks --stack-name "$STACK" \
        --query "Stacks[0].Outputs[?OutputKey=='$1'].OutputValue" --output text
}

VPC_ID=$(aws ec2 describe-vpcs --filters Name=is-default,Values=true --query 'Vpcs[0].VpcId' --output text)

echo "==> Phase 1: base resources (RDS takes ~10 minutes)"
deploy

REPOSITORY_URI=$(output RepositoryUri)
SERVICE_ARN=$(output ServiceArn)

if [[ -n ${IMAGE_URI:-} ]]; then
    echo "==> Using IMAGE_URI=$IMAGE_URI"
elif [[ -n $SERVICE_ARN && $SERVICE_ARN != None ]]; then
    # Re-run: keep the image GitHub Actions last deployed instead of rolling the service back.
    IMAGE_URI=$(aws ecs describe-express-gateway-service --service-arn "$SERVICE_ARN" \
        --query 'service.activeConfigurations[0].primaryContainer.image' --output text)
    echo "==> Keeping the running image $IMAGE_URI"
else
    # First run: the Express service needs an image to start from.
    IMAGE_URI=$REPOSITORY_URI:initial-$(git rev-parse --short HEAD)
    echo "==> Building and pushing $IMAGE_URI"
    ./gradlew bootJar
    aws ecr get-login-password | docker login --username AWS --password-stdin "${REPOSITORY_URI%%/*}"
    # Fargate runs x86_64 by default; build for it even on an Apple Silicon Mac.
    docker buildx build --platform linux/amd64 -t "$IMAGE_URI" --push .
fi

echo "==> Phase 2: Express service"
deploy "ImageUri=$IMAGE_URI"

LOAD_BALANCER_ARN=$(output LoadBalancerArn)
LISTENER_RULE_ARN=$(output ListenerRuleArn)

echo "==> Adding $DOMAIN to the Express listener rule's host-header condition"
# Express only forwards requests for its generated hostname; the rule must also match the custom domain.
# Express Mode keeps this change on later Express updates (see "Updating Resources Outside of Express Mode").
aws elbv2 describe-rules --rule-arns "$LISTENER_RULE_ARN" --query 'Rules[0].Conditions' --output json \
    | jq --arg domain "$DOMAIN" 'map(
        if .Field == "host-header"
        then {Field, HostHeaderConfig: {Values: ((.HostHeaderConfig.Values + [$domain]) | unique)}}
        else del(.Values) end)' > /tmp/wholesale-rule-conditions.json
aws elbv2 modify-rule --rule-arn "$LISTENER_RULE_ARN" \
    --conditions file:///tmp/wholesale-rule-conditions.json --query 'Rules[0].Conditions' --output json

read -r ALB_DNS_NAME ALB_HOSTED_ZONE_ID < <(aws elbv2 describe-load-balancers \
    --load-balancer-arns "$LOAD_BALANCER_ARN" \
    --query 'LoadBalancers[0].[DNSName,CanonicalHostedZoneId]' --output text)

echo "==> Phase 3: DNS record $DOMAIN -> $ALB_DNS_NAME"
deploy "ImageUri=$IMAGE_URI" "AlbDnsName=$ALB_DNS_NAME" "AlbHostedZoneId=$ALB_HOSTED_ZONE_ID"

echo "==> Pointing GitHub Actions at the new stack"
gh variable set AWS_ROLE_ARN --body "$(output GitHubDeployRoleArn)"
gh variable set EXPRESS_SERVICE_ARN --body "$(output ServiceArn)"

echo
echo "Done. Generated endpoint: $(output ServiceEndpoint)"
echo "Custom domain (allow a few minutes for DNS): https://$DOMAIN/actuator/health"
