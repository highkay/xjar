FROM adoptopenjdk/openjdk8:alpine-jre
WORKDIR /opt/xjar
COPY target/xjar-4.0.2.jar /opt/xjar/
ENTRYPOINT ["java","-jar","/opt/xjar/xjar-4.0.2.jar"]