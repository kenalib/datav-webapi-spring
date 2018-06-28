package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("airData")
class AirDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirDataService.class);
    @Autowired
    private ResourceLoader resourceLoader;

    private HashMap<String, String[]> cities = new HashMap<>();
    private HashMap<String, float[]> positions = new HashMap<>();
    private HashMap<String, Integer> siteColNum = new HashMap<>();

    @PostConstruct
    public void init() {
        readSiteList();
    }

    private void readSiteList() {
        String filename = "classpath:csv/site_list.csv";
        Resource resource = resourceLoader.getResource(filename);

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
                String[] cityName = {rec.get(2), rec.get(1)};

                cities.put(code, cityName);

                String latString = rec.get(4);
                String lngString = rec.get(3);

                if (latString.equals("") || lngString.equals("")) continue;

                float lat = Float.parseFloat(latString);
                float lng = Float.parseFloat(lngString);

                float position[] = {lat, lng};

                positions.put(code, position);
                i++;
            }

        } catch (Exception e) {
            System.out.println("Read Err site_list.csv at " + i);
            e.printStackTrace();
        }
    }

    HashMap<String, String[]> getCities() {
        return cities;
    }

    HashMap<String, float[]> getPositions() {
        return positions;
    }

    float[] getPosition(String code) {
        return positions.get(code);
    }

    List<GeoData> findAirData(String dateHour, String type) {
        String date = dateHour.substring(0, 8);
        String hour = dateHour.substring(8);

        LOGGER.info("findAirData(" + date + "-" + hour + ", " + type + ")");

        String filename = "classpath:csv/china_sites_" + date + ".csv";
        Resource resource = resourceLoader.getResource(filename);
        List<GeoData> geoDataList = new ArrayList<>();

        try {
            Reader reader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(reader);

            int i = 0;
            int colSize = 0;
            String line;

            while((line = br.readLine()) != null) {
                if (i == 0) {
                    colSize = createSiteColNumData(line);
                    i++;
                    continue;
                }

                String[] data = line.split(",", colSize);
                String hour_ = data[1];
                String type_ = data[2];

                if (hour.equals(hour_) && type_.equals(type)) {
                    geoDataList = createAirDataList(data);
                    break;
                }

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return geoDataList;
    }

    private int createSiteColNumData(String line) {
        String[] headers = line.split(",");

        int j = 0;

        for (String header : headers) {
            if (j < 3) {
                j++;
                continue;
            }

            siteColNum.put(header, j);
            j++;
        }

        return j;
    }

    private List<GeoData> createAirDataList(String[] data) {
        List<GeoData> geoDataList = new ArrayList<>();

        for (String code : siteColNum.keySet()) {
            float[] position = positions.get(code);
            if (position == null) continue;

            Integer index = siteColNum.get(code);
            if (index == null) continue;

            String rawData = data[index];
            if (rawData.equals("")) continue;

            int value = Integer.parseInt(rawData);
            GeoData geoData = new GeoData(code, value , position[0], position[1]);
            geoDataList.add(geoData);
        }

        return geoDataList;
    }
}
