FROM amazoncorretto:17.0.8
RUN mkdir notificationservice
COPY /target/notificationservice-0.0.1-SNAPSHOT.war /notificationservice
WORKDIR notificationservice/
CMD ["java", "-jar", "notificationservice-0.0.1-SNAPSHOT.war"]