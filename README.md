# Library Warehouse application

![CI status](https://github.com/xpinjection/library-warehouse/actions/workflows/maven.yml/badge.svg)

Sample Spring Boot cloud-native application, a consumer of the [Library application](https://github.com/xpinjection/test-driven-spring-boot/) REST API for books management.

## Consumer-driven contracts

[Pact](https://docs.pact.io/) is used to describe and manage REST API contracts.

To generate and publish pacts following command could be used: `mvn clean package pact:publish -Dpact.skip.publish=false`.

Use system variable `pact.skip.publish` to control generated pacts publishing to the Pact Broker. The Pact Broker configuration is located in `pom.xml` and could be overridden with system properties as well.

System properties `pact.consumer.version` and `pact.branch.name` should be used to pass correct version of the application and git branch for tracking in the Pact Broker.
