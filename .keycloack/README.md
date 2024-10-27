## Brief Description

This Dockerfile will be used for local environment testing and development. It will build a Docker image for the Keycloak server with the necessary configurations for the Incident Management API project. The image will include the Keycloak server, a realm, a client, and a user with the necessary roles and permissions.

- **PROD Env:**
  - Prod environment will be built in the DevOps repository with a separate Dockerfile and configurations.

## Troubleshooting

- **Shadow Error**: If you cannot enter the Keycloak admin console, you may need to clear need remap the port (8080) to another port in the Dockerfile because it may be in use.
- **SSL Error**: As we do not have SSL certificates, you may need to disable SSL in the Keycloak server configuration.
  - To disable SSL, you need to add some ENV variables in the Dockerfile.
  - Also you need to take into an account that if you specify networks in the docker-compose file you may have SSL problems.