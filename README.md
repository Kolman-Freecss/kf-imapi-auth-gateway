- IMAPI Angular service: https://github.com/Kolman-Freecss/kf-imapi-angular
- IMAPI Incident Service: https://github.com/Kolman-Freecss/kf-imapi-incident-service
- IMAPI Response Team Service: https://github.com/Kolman-Freecss/kf-imapi-response-service
- IMAPI Notification Service: https://github.com/Kolman-Freecss/kf-imapi-notification-service
- IMAPI Authentication Gateway Service: https://github.com/Kolman-Freecss/kf-imapi-auth-gateway
- IMAPI DevOps / Kafka Event Handling: https://github.com/Kolman-Freecss/kf-imapi-devops

## Endpoint Utils

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
- Zipkin: http://localhost:9411
- Keycloak: http://localhost:8080
- Eureka: http://localhost:8761

## Brief Description

Features: 
- Implementation of security using Spring Boot 3.3 and Keycloak with JSON Web Tokens (JWT).
- API Gateway for routing requests to the appropriate service.
  - Circuit Breaker using Resilience4j.
  - Also we hide the internal services from the outside world and KEYCLOAK (everything is behind the gateway).
- SSO (Single Sign-On) using Keycloak.
  - Each token is signed by Keycloak and validated by the API Gateway to access to all the services.
- OAuth2 Protocol.
- Internal JWT signing to validate the token in every microservice verifying the authenticity of the API Gateway token. (X-Internal-Auth)
  - (Another robust option is to sign every token through TLS, but it is not implemented in this project).
- Redis for caching. As request limiters.
- Load Balancer using Eureka.
- Testing
  - Integration tests with Groovy and Spock. 
  - Performance tests with JMeter.
  - Concurrent tests with Virtual Threads (JDK 19).

## Getting Started

- Configure Keycloak with the following settings:
  - New Realm: `imapi`
  - Create a new Client: `imapi-gateway`
    - Client ID: `imapi-gateway`
  - 2 Roles: `USER` and `ADMIN`
  - 2 Users: `user` and `admin`
    - Password: `password`
    - Map Roles to the users: `USER` and `ADMIN`

**Diagram Flow:**

![Diagram Flow](Diagram-Flow.svg)

## Tech stack:

- Spring Boot 3.0
- Keycloak
- JSON Web Tokens (JWT)
- Maven
- Docker
- Spring Cloud Gateway
- Spring Cloud Netflix (Eureka)
- Resilence4j (Circuit Breaker)
- Observability - Micrometer Tracing and Zipkin (Distributed Tracing) (Sleuth is deprecated)
  - We use AOP support to decorate the methods with tracing annotations.
- Prometheus and Grafana (Monitoring)
- Redis (Caching rate limiter)
  - We can use Redis Insights (Client) to check the cache.

## TroubleShooting

- To wire all services through gateway ensure you add correctly the names and no duplicate services are up in same time.
- We need to use a specific filters to modify the immutable request headers because not in every phase of the request lifecycle we can modify the headers.
  - Also we need 2 filter, because not all traffic is going through the same spring path
- Implementing the Cache with Redis at the Gateway level to limit the requests to the services.
  - For some reason the cache with responses commited and stuff like that is not working properly.


---

Shield: [![CC-BY-NC-ND 4.0][CC-BY-NC-ND-shield]][CC-BY-NC-ND]

This work is licensed under a [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.][CC-BY-NC-ND]

[![CC-BY-NC-ND 4.0][CC-BY-NC-ND-image]][CC-BY-NC-ND]

[CC-BY-NC-ND-shield]: https://img.shields.io/badge/License-CC--BY--NC--ND--4.0-lightgrey
[CC-BY-NC-ND]: http://creativecommons.org/licenses/by-nc-nd/4.0/
[CC-BY-NC-ND-image]: https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png
