ARG DOCKER
FROM $DOCKER
ENV JAVA_OPTS=""

WORKDIR /rococo
COPY ./gradle ./gradle
COPY ./gradlew ./
COPY ./rococo-e-2-e-tests ./rococo-e-2-e-tests
COPY ./build.gradle ./
COPY ./settings.gradle ./

CMD chmod +x ./rococo-e-2-e-tests/send_results.sh; ./gradlew test -Dtest.env=docker; bash ./rococo-e-2-e-tests/send_results.sh
