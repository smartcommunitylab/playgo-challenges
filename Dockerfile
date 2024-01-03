FROM maven:3-eclipse-temurin-11-alpine AS build
ARG REPO
RUN apk --no-cache add git
RUN --mount=type=cache,target=/root/.m2,source=/root/.m2,from=smartcommunitylab/challenges-generator:cache git clone -b $REPO https://github.com/smartcommunitylab/game-engine.challenge-gen.git && cd game-engine.challenge-gen && mvn clean install -Dmaven.test.skip=true
ADD pom.xml /tmp
ADD src/ /tmp/src/ 
WORKDIR /tmp
RUN --mount=type=cache,target=/root/.m2,source=/root/.m2,from=smartcommunitylab/challenges-generator:cache mvn clean package -P full-client-jar -DskipTests

FROM eclipse-temurin:11-alpine
ARG USER=challenges
ARG USER_ID=814
ARG USER_GROUP=challenges
ARG USER_GROUP_ID=814
ARG USER_HOME=/home/${USER}
ENV FOLDER=/tmp/target
ENV APP=challenges-jar-with-dependencies.jar
# create a user group and a user
RUN  addgroup -g ${USER_GROUP_ID} ${USER_GROUP}; \
     adduser -u ${USER_ID} -D -g '' -h ${USER_HOME} -G ${USER_GROUP} ${USER} ;

WORKDIR ${USER_HOME}
COPY --from=build /tmp/target/challenges.jar ${USER_HOME}
USER challenges
ENTRYPOINT ["java", "-jar", "challenges.jar", "--spring.profiles.active=sec"]