FROM adoptopenjdk:15-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./catalogue-api/target/catalogue-1.0-SNAPSHOT.jar /app

EXPOSE 3000

CMD ["java", "-jar", "catalogue-1.0-SNAPSHOT.jar"]