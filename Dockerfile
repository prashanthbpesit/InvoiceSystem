FROM
:21-jdk-slim
VOLUME /tmp
COPY target/invoice-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
