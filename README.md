- IMAPI Angular service: https://github.com/Kolman-Freecss/kf-imapi-angular
- IMAPI Incident Service: https://github.com/Kolman-Freecss/kf-imapi-incident-service
- IMAPI Response Team Service: https://github.com/Kolman-Freecss/kf-imapi-response-service
- IMAPI Notification Service: https://github.com/Kolman-Freecss/kf-imapi-notification-service
- IMAPI Authentication Gateway Service: https://github.com/Kolman-Freecss/kf-imapi-auth-gateway
- IMAPI DevOps / Kafka Event Handling: https://github.com/Kolman-Freecss/kf-imapi-devops

## Brief Description

Features: 
- Implementation of security using Spring Boot 3.0 and Keycloack with JSON Web Tokens (JWT).
- API Gateway for routing requests to the appropriate service.
- SSO (Single Sign-On) using Keycloack.
- OAuth2 Protocol.
- Internal JWT signing to validate the token in every microservice verifying the authenticity of the API Gateway token. (X-Internal-Auth)
  - (Another robust option is to sign every token through TLS, but it is not implemented in this project).

## Getting Started

- Configure Keycloack with the following settings:
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
- Keycloack
- JSON Web Tokens (JWT)
- Maven
- Docker
- Spring Cloud Gateway
- Spring Cloud Netflix (Eureka)
- Resilence4j (Circuit Breaker)
- Sleuth and Zipkin (Distributed Tracing)
- Prometheus and Grafana (Monitoring)

## TroubleShooting

- To wire all services through gateway ensure you add correctly the names and no duplicate services are up in same time.


---

Shield: [![CC-BY-NC-ND 4.0][CC-BY-NC-ND-shield]][CC-BY-NC-ND]

This work is licensed under a [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.][CC-BY-NC-ND]

[![CC-BY-NC-ND 4.0][CC-BY-NC-ND-image]][CC-BY-NC-ND]

[CC-BY-NC-ND-shield]: https://img.shields.io/badge/License-CC--BY--NC--ND--4.0-lightgrey
[CC-BY-NC-ND]: http://creativecommons.org/licenses/by-nc-nd/4.0/
[CC-BY-NC-ND-image]: https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png
