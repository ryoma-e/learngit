FROM maven:3-jdk-8-alpine

ARG jarname
WORKDIR /usr/src/app

COPY target/${jarname} /usr/src/app

ENV PORT 5000
EXPOSE $PORT

CMD [ "sh", "-c", "java -Dserver.port=${PORT} -jar /usr/src/app/${jarname}" ]
