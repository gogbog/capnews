FROM jboss/keycloak

USER root

COPY providers/keycloak-to-rabbit-3.0.2.jar /opt/jboss/keycloak/standalone/deployments/rab.jar

RUN chown -R jboss:jboss /opt/jboss/keycloak/standalone/deployments && \
    chmod -R 755 /opt/jboss/keycloak/standalone/deployments

USER jboss

