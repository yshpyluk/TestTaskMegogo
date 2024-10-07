FROM openjdk:17

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

CMD ["mvn", "test"]
