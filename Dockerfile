
FROM eclipse-temurin:22-jdk AS buildstage 

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_DbCapstone /app/oracle_wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package

FROM eclipse-temurin:22-jdk 

COPY --from=buildstage /app/target/ms-gestión-notificaciones-0.0.1.jar /app/ms-gestión-notificaciones-0.0.1.jar

COPY Wallet_DbCapstone /app/oracle_wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8084

ENTRYPOINT [ "java", "-jar","/app/ms-gestión-notificaciones-0.0.1.jar" ]