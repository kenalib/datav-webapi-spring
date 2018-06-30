package com.example;

import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AirDataParser {
    private HashMap<String, Location> locations;
    private Resource resource;
    private HashMap<String, Integer> siteColNum = new HashMap<>();

    AirDataParser(Resource resource, HashMap<String, Location> locations) {
        this.locations = locations;
        this.resource = resource;
    }

    List<HeatmapData> parseAirData(String hour, String type) {
        List<HeatmapData> heatmapDataList = new ArrayList<>();

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
                    heatmapDataList = createAirDataList(data);
                    break;
                }

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return heatmapDataList;
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

    private List<HeatmapData> createAirDataList(String[] data) {
        List<HeatmapData> heatmapDataList = new ArrayList<>();

        for (String code : siteColNum.keySet()) {
            Location location = locations.get(code);
            if (location == null) continue;

            Integer index = siteColNum.get(code);
            if (index == null) continue;

            String rawData = data[index];
            if (rawData.equals("")) continue;

            int value = Integer.parseInt(rawData);
            HeatmapData heatmapData = new HeatmapData(code, value, location);
            heatmapDataList.add(heatmapData);
        }

        return heatmapDataList;
    }

}
