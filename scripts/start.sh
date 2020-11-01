#!/bin/sh

java -cp app:app/lib/* com.anz.wholesale.WholesaleApplication \
          -Dspring.datasource.url=${SPRING_DATASOURCE_URL} \
          -Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} \
          -Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}