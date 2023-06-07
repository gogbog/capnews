FROM jboss/keycloak

USER root

COPY providers /opt/jboss/keycloak/standalone/deployments

RUN chown -R jboss:jboss /opt/jboss/keycloak/standalone/deployments && \
    chmod -R 755 /opt/jboss/keycloak/standalone/deployments

USER jboss

