package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("flyingRoutes")
class FlyingRoutesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyingRoutesService.class);

    @Value("${settings.expire_minutes}")
    private int expireMinutes;
    @Value("${settings.check_duration}")
    private int checkDuration;

    @Autowired
    private ResourceLoader resourceLoader;

    private HashMap<String, Location> cities = new HashMap<>();
    private List<RouteData> routes = new ArrayList<>();
    private LocalDateTime lastCheckTime = LocalDateTime.now();

    @PostConstruct
    void init() {
        LOGGER.info("Initializing FlyingRoutesService...");

        String filename = "classpath:location/japan-cities.csv";
        Resource resource = resourceLoader.getResource(filename);
        JapanCitiesParser parser = new JapanCitiesParser(resource);

        cities = parser.getCities();
    }

    HashMap<String, Location> getCities() {
        return cities;
    }

    List<RouteData> findFlyingRoutes() {
        LocalDateTime now = LocalDateTime.now();

        if (ChronoUnit.MINUTES.between(lastCheckTime, now) > checkDuration) {
            expireRoutes();
        }

        return routes;
    }

    private void expireRoutes() {
        LOGGER.info("expireRoutes... if older than " + expireMinutes + " minutes");

        routes.removeIf(route -> route.isExpired(expireMinutes));

        lastCheckTime = LocalDateTime.now();
    }

    List<RouteData> saveFlyingRoutes(String routeCsv, String mode) {
        return saveFlyingRoutes(routeCsv, mode, null);
    }

    List<RouteData> saveFlyingRoutes(String routeCsv, String mode, Integer expireMinutes) {
        LOGGER.info("saveFlyingRoutes " + mode + " expires in " + expireMinutes);

        if (mode != null && !mode.equals("append")) {
            routes.clear();
        }

        if (expireMinutes != null) {
            this.expireMinutes = expireMinutes;
        }

        for (String row: routeCsv.split("[\r\n]+")) {
            if (row == null) continue;

            String[] col = row.split(",", 2);
            String from = col[0];
            String to = col[1];

            if (!(cities.containsKey(from) && cities.containsKey(to))) continue;

            routes.add(new RouteData(cities.get(from), cities.get(to)));
        }

        return routes;
    }

}
