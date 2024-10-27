# Use the official Keycloak image
FROM quay.io/keycloak/keycloak:26.0 AS keycloak

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Set necessary environment variables
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

# Set default language to English (en-US)
ENV KEYCLOAK_LOCALE_DEFAULT=en

# Disable SSL mode
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME_STRICT_HTTPS=false
ENV KC_HTTP_ENABLED=true

## --- Optional: Enable HTTPS for Keycloak ---
# Configure Keycloak to use HTTPS
#ENV KEYCLOAK_HTTPS_PORT=8443
# Copy SSL certificates to the appropriate directory
#COPY keycloak.crt /etc/x509/https/tls.crt
#COPY keycloak.key /etc/x509/https/tls.key
# Set the Keycloak HTTPS certificate and private key
#ENV KEYCLOAK_HTTPS_CERTIFICATE=/etc/x509/https/tls.crt
#ENV KEYCLOAK_HTTPS_PRIVATE_KEY=/etc/x509/https/tls.key

# Start Keycloak with HTTPS enabled
#ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--hostname-strict=false"]
## -------------------------------------------

# Start Keycloak without HTTPS
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]

# Expose the ports
EXPOSE 8080