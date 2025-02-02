# Incident Management API with Microservices
## Project Overview
This project is a RESTful API built in Java to manage incidents within an organization. 
The API will implement CRUD (Create, Read, Update, Delete) operations for three main entities: Incident, ResponseTeam, and Notification. 
Each microservice will handle one of these entities and communicate with the others through Kafka message events.

## Project Architecture
The project is designed in a microservices architecture connected via Apache Kafka:

- Incident Service: Manages reported incidents.
- Response Team Service: Manages the response teams assigned to each incident.
- Notification Service: Sends notifications about incident status updates to users.
- Authentication Gateway Service: Manages user authentication and authorization.

## Authentication Service (API Gateway)

An Authentication Service acting as an API Gateway is a highly effective approach in a microservices architecture. It centralizes authentication and authorization, simplifying security management and keeping each business microservice focused on its core logic. Here’s how this Authentication Service could be structured:

### Key Features of the Authentication Service
1. Authentication: Verifies user identity using tokens (e.g., JWT) or sessions.
2. Authorization: Validates permissions for each endpoint and resource requested.
3. Routing: Forwards valid requests to the respective microservices, ensuring only authorized requests proceed.
4. Session and Token Management: Issues, verifies, expires, and renews tokens as needed.

### Additional Security Enhancements
- Token Refresh: Allows users to obtain a new token when the current one expires.
- Rate Limiting: To prevent brute force and denial-of-service (DDoS) attacks.
- Security Logs: Records every login attempt, failed authentication, and suspicious activity, with alert mechanisms.

Proposed Authentication Flow
1. Login:

- Endpoint POST /auth/login: Users submit credentials; if valid, the service issues a JWT (or access token) and a refresh token.
- Tokens have a limited duration to enhance security. 
2. Token Validation:

- Each request that reaches this gateway includes the JWT in the authorization header (e.g., Authorization: Bearer <token>).
- The gateway validates the token, checking for expiration and user permissions. If valid, the request is forwarded to the corresponding microservice.
3. Token Renewal:

- Endpoint POST /auth/refresh: Allows the client to obtain a new JWT using the refresh token.
4. Logout:

- Endpoint POST /auth/logout: Invalidates the current access token, logging the user out and revoking the refresh token.

### API Gateway as the Central Security Layer
- Easy Maintenance and Scalability: Microservices delegate authentication to the gateway, removing the need to handle tokens or credentials in each service.
- Adaptable to Various Security Models: The gateway can implement basic authentication, JWT, OAuth2, or integrate with an identity provider (IdP) like Auth0 or Keycloak if SSO (Single Sign-On) is needed.

### Example Endpoints for the Authentication Service
- POST /auth/login - Authenticates and issues tokens.
- POST /auth/refresh - Renews the access token.
- POST /auth/logout - Ends the user session.
- GET /auth/validate - Validates tokens (optional but useful for verification).


___


## Kafka Event Handling
Each microservice listens to and produces events on Kafka:

1. Incident Service: Produces events whenever an incident is created or its status changes.
2. Response Team Service: Listens for events to automatically assign response teams to critical incidents, producing events when a team is assigned.
3. Notification Service: Listens for events and sends automatic notifications when an incident is created or changes status.

___ 
## Technical Requirements
- Data Validation: Each service should include data validation and detailed error handling.
- Swagger/OpenAPI: Automatic documentation generation for each microservice.
- Security: Basic authentication implementation for the microservices' endpoints.
- Logging and Monitoring: Services will generate structured logs to monitor event activity and operation status.

___

This architecture and structure will allow high scalability and modularity in incident management, distributing responsibilities across three well-defined microservices connected through Kafka. It facilitates a clear separation of business logic and maintains a low coupling between services, making it ideal for production environments where stability and performance are essential.
