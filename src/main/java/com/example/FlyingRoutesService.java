package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("flyingRoutes")
class FlyingRoutesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlyingRoutesService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    private HashMap<String, Location> cities = new HashMap<>();
    private List<RouteData> routes = new ArrayList<>();

    @PostConstruct
    void init() {
        LOGGER.info("Initializing FlyingRoutesService...");

        String filename = "classpath:location/japan-cities.csv";
        Resource resource = resourceLoader.getResource(filename);
        JapanCitiesParser parser = new JapanCitiesParser(resource);

        cities = parser.getCities();
    }

    List<RouteData> findFlyingRoutes() {
        return routes;
    }

    List<RouteData> findFlyingRoutes(String routeCsv, String mode) {
        LOGGER.info("findFlyingRoutes... " + mode);

        if (mode != null && !mode.equals("append")) {
            routes = new ArrayList<>();
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
