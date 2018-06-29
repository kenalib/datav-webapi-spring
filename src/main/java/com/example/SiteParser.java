package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

class SiteParser {

    private HashMap<String, Site> sites = new HashMap<>();
    private HashMap<String, Location> locations = new HashMap<>();

    SiteParser(Resource resource) {
        parseSiteList(resource);
    }

    private void parseSiteList(Resource resource) {
        int i = 0;

        try {
            Reader reader = new InputStreamReader(resource.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord rec : csvParser) {
                if (i == 0) {
                    i++;
                    continue;
                }

                String code = rec.get(0);
                Site site = new Site(rec.get(2), rec.get(1));
                sites.put(code, site);

                String latString = rec.get(4);
                String lngString = rec.get(3);

                if (latString.equals("") || lngString.equals("")) continue;

                float lat = Float.parseFloat(latString);
                float lng = Float.parseFloat(lngString);
                locations.put(code, new Location(lat, lng));

                i++;
            }

        } catch (Exception e) {
            System.out.println("Read Err site_list.csv at " + i);
            e.printStackTrace();
        }
    }

    HashMap<String, Site> getSites() {
        return sites;
    }

    HashMap<String, Location> getLocations() {
        return locations;
    }
}
