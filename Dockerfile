FROM openjdk:17


ADD target/cloudpi_backend-0.0.9.jar .

EXPOSE 8080
CMD java -jar cloudpi_backend-0.0.9.jar -Dspring.profiles.active=dev-deploy