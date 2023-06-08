FROM openjdk:11 as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} edge.jar
RUN java -Djarmode=layertools -jar edge.jar extract

FROM openjdk:11
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
EXPOSE 9090
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]