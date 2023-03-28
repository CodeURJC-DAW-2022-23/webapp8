#########################
# COMPILATION CONTAINER #
#########################
# Base image to build the project
FROM maven:3.8.4-openjdk-17 as builder

# Choose container working directory where we are going to execute commands
WORKDIR /project

# Copy project dependencies from local project to compilation container directory
COPY pom.xml /project

# Execute command in compilation container to download project dependencies
RUN mvn clean verify --fail-never

# Copy project source from local project to compilation container directory
COPY /src /project/src

# Compile project using already downloaded libraries
RUN mvn package -DskipTests

#########################
# APPLICATION CONTAINER #
#########################
FROM openjdk:17.0.2

# Choose container working directory where we can find JAR file
WORKDIR /usr/app

# Copy JAR from compilation container to application container directory
COPY --from=builder /project/target/*.jar /usr/app

# Start expose port from container
EXPOSE 8443

# Execute command
CMD [ "java", "-jar", "ProjectBackend-0.0.1-SNAPSHOT.jar" ]
