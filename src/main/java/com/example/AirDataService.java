package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Service("airData")
class AirDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirDataService.class);
    @Autowired
    private ResourceLoader resourceLoader;

    private HashMap<String, Site> sites = new HashMap<>();
    private HashMap<String, Location> locations = new HashMap<>();

    @PostConstruct
    void init() {
        String filename = "classpath:csv/site_list.csv";
        Resource resource = resourceLoader.getResource(filename);
        SiteParser parser = new SiteParser(resource);

        sites = parser.getSites();
        locations = parser.getLocations();
    }

    HashMap<String, Site> getSites() {
        return sites;
    }

    HashMap<String, Location> getLocations() {
        return locations;
    }

    Location getLocation(String code) {
        return locations.get(code);
    }

    List<HeatmapData> findAirData(String dateHour, String type) {
        String date = dateHour.substring(0, 8);
        String hour = dateHour.substring(8);

        LOGGER.info("findAirData(" + date + "-" + hour + ", " + type + ")");

        String filename = "classpath:csv/china_sites_" + date + ".csv";
        Resource resource = resourceLoader.getResource(filename);
        AirDataParser parser = new AirDataParser(resource, locations);

        return parser.parseAirData(hour, type);
    }

}
