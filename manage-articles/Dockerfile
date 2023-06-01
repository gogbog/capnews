FROM openjdk:17-jdk

# Download and install Maven
RUN curl -fsSL https://dlcdn.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.tar.gz | tar xfz - -C /usr/share \
    && mv /usr/share/apache-maven-3.9.1 /usr/share/maven \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn



COPY manage-articles/pom.xml .
COPY manage-articles/src/ ./src/
COPY manage-articles/images/ ./images/

RUN  mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/manage-articles-0.0.1-SNAPSHOT.jar"]

