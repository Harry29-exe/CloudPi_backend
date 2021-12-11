FROM maven:3.8.4-openjdk-17 as build
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml verify clean --fail-never
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17
COPY --from=build /home/app/target/cloudpi_backend-0.0.9.jar /usr/local/bin/CloudPi.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/bin/CloudPi.jar"]
CMD java -jar cloudpi_backend-0.0.9.jar -Dspring.profiles.active=dev-deploy