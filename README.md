
# DataV Quick Start

This doc follows DataV Quick Start document.

* https://jp.alibabacloud.com/help/doc-detail/59606.htm


## Download data

* http://beijingair.sinaapp.com/

To make it quick, we download only one day data.

### one day data

* http://beijingair.sinaapp.com/data/china/sites/20170127/csv

### site list data

* http://beijingair.sinaapp.com/ => National Air Quality Data Baidu SkyDrive Download
* download 站点列表(含经纬度)-新-1497个.csv as `site_list.csv`


## Test and Run

```bash
mvn test
mvn spring-boot:run
open http://localhost:8080/air-data/aqi?date=2017012722
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
```

## Reference

* https://spring.io/guides/gs/rest-service/
