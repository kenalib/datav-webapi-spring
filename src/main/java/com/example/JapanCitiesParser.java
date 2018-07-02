package com.example;

import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

class JapanCitiesParser {

    private HashMap<String, Location> cities = new HashMap<>();

    JapanCitiesParser(Resource resource) {
        parseCities(resource);
    }

    HashMap<String, Location> getCities() {
        return cities;
    }

    private void parseCities(Resource resource) {
        try {
            Reader reader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(reader);

            String row;

            while((row = br.readLine()) != null) {
                String[] col = row.split(",");

                String lat = col[3];
                String lng = col[4];

                Location location = new Location(lat, lng);

                String pref = col[1];
                String city = col[2];

                cities.put(pref, location);
                cities.put(city, location);
                cities.put(pref.substring(0, pref.length()-1), location);
                cities.put(city.substring(0, city.length()-1), location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
