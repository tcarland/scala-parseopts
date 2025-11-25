ARG java_image_tag=21
FROM azul/zulu-openjdk:${java_image_tag}

LABEL org.opencontainers.image.authors="Timothy C. Arland <tcarland at gmail.com>" \
      org.opencontainers.image.base.name="azul/zulu-openjdk:21" \
      org.opencontainers.image.description="Maven Build Test Container" \
      org.opencontainers.image.source="https://github.com/tcarland/scala-parseopts" \
      org.opencontainers.image.title="scala-parseopts" 

RUN apt-get update && \
    apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*

COPY . /scala-parseopts
WORKDIR /scala-parseopts

RUN mvn clean package -Pscala-2.13 -DskipTests
