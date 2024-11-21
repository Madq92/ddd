FROM openjdk:17.0.2 AS builder
WORKDIR /application
ARG JAR_FILE=ddd-start/target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract


FROM openjdk:17.0.2
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
WORKDIR /application
EXPOSE 8080
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]

