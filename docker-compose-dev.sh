#!/bin/bash
source ./docker.properties
export PROFILE="${PROFILE:=docker}"

echo '### Java version ###'
java --version

front=""
front_image=""

front="./rococo-client/";
front_image="${IMAGE_PREFIX}/${FRONT_IMAGE_NAME}-${PROFILE}:latest";


FRONT_IMAGE="$front_image" PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker-compose down

docker_containers="$(docker ps -a -q)"
docker_images="$(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')"

if [ ! -z "$docker_containers" ]; then
  echo "### Stop containers: $docker_containers ###"
  docker stop $(docker ps -a -q)
  docker rm $(docker ps -a -q)
fi
if [ ! -z "$docker_images" ]; then
  echo "### Remove images: $docker_images ###"
  docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')
fi

if [ "$1" = "push" ] || [ "$2" = "push" ]; then
  echo "### Build & push images (front: $front) ###"
  bash ./gradlew -Pskipjaxb jib -x :rococo-e-2-e-tests:test
  cd "$front" || exit
  bash ./docker-build.sh ${PROFILE} push
else
  echo "### Build images (front: $front) ###"
  bash ./gradlew -Pskipjaxb -Djib.dockerClient.executable=/usr/local/bin/docker jibDockerBuild -x :rococo-e-2-e-tests:test
  cd "$front" || exit
  bash ./docker-build.sh ${PROFILE}
fi

cd ../
docker images
FRONT_IMAGE="$front_image" PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker-compose up -d
docker ps -a
