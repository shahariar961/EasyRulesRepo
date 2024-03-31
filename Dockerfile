# Start with a base image containing Java runtime and Maven
FROM maven:3.9.6-amazoncorretto-21-debian as build

# The application's working directory
WORKDIR /usr/src/app

# Copy the pom.xml file
COPY ./pom.xml ./pom.xml

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application's source code
COPY ./src ./src

# Build the project
RUN mvn package

# Start with a base image containing Java runtime
FROM  openjdk:21-jdk as runtime

# Copy the jar file from the build stage
COPY --from=build /usr/src/app/target/*-jar-with-dependencies.jar /app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]