FROM openjdk:14 AS builder
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN ./gradlew clean build

FROM openjdk:14
COPY --from=builder /usr/src/opening-hours/build/libs/movie-recommender-app-0.0.1-SNAPSHOT.jar /usr/src/app/
WORKDIR /usr/src/opening-hours
EXPOSE 8080
CMD ["java", "-jar", "movie-recommender-app-0.0.1-SNAPSHOT.jar"]