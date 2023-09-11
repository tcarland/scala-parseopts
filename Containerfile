ARG java_image_tag=17-jre

FROM eclipse-temurin:${java_image_tag}

RUN apt-get update && apt-get install -y --no-install-recommends \
    maven \
    && rm -rf /var/lib/apt/lists/*

COPY . /scala-parseopts
WORKDIR /scala-parseopts

RUN mvn clean package -DskipTests
