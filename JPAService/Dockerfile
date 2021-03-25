FROM openjdk
WORKDIR /usr/src
ENV MYSQL_HOST=localhost
ENV MYSQL_DATABASE=testdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
COPY ./target/JPAService-0.0.1-SNAPSHOT.jar JPAService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/src/JPAService-0.0.1-SNAPSHOT.jar"]