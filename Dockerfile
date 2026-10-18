FROM eclipse-temurin:21-jdk
RUN mkdir -p /opt/demo-academico
ENV TZ="America/Bogota"
ENV WORKSPACE /opt/demo-academico
# application.yml usa 8081; en el contenedor se fuerza 8080 para que coincida con EXPOSE y el mapeo 8080:8080
ENV SERVER_PORT=8080
WORKDIR ${WORKSPACE}
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xmx1024m","-jar","app.jar"]
