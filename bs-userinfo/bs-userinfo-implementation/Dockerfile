FROM openjdk:17-alpine

EXPOSE 8581

COPY ./target/bs-userinfo-implementation-*.jar ROOT.jar

ENTRYPOINT ["java", "-jar", "ROOT.jar"]