
# DataV Tools


## Heat Map

[Heat Map README](./README-heat-map.md)


## Flying Routes

[Flying Routes README](./README-flying-routes.md)


## Test and Run

```bash
mvn test
mvn spring-boot:run
open http://localhost:8080/air-data/aqi?date=2017012722
open http://localhost:8080/flying-routes/japan
```

## Run from DockerHub

* https://spring.io/guides/gs/spring-boot-docker/

```bash
mvn package
# sample docker ID: kenali (update to your ID in pom.xml docker.image.prefix)
mvn dockerfile:build
mvn dockerfile:push
# run manually in case of push error
docker push kenali/datav-quick-start:latest
```

```bash
docker run -p 80:8080 -t kenali/datav-quick-start
open http://localhost/air-data/aqi?date=2017012722
open http://localhost/flying-routes.html
```

## Reference

* https://spring.io/guides/gs/rest-service/
