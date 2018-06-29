package com.example;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirDataServiceTest extends TestCase {

    @Autowired
    AirDataService service;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void init() {
        assertNotNull(service);
        service.init();

        assertEquals(1497, service.getSites().size());
        assertEquals(1482, service.getLocations().size());
        assertEquals(39.8673F, service.getLocation("1001A").getLat());
        assertEquals(116.366F, service.getLocation("1001A").getLng());
        assertEquals(23.1323F, service.getLocation("2846A").getLat());
        assertEquals(113.3208F, service.getLocation("2846A").getLng());
    }

    @Test
    public void findAirData() {
        List<HeatmapData> airData = service.findAirData("2017012722", "AQI");
        assertEquals(1387, airData.size());

        for (HeatmapData heatmapData : airData) {
            if (heatmapData.getCode().equals("1001A")) {
                assertEquals(371, heatmapData.getValue());
            }
        }
    }

    @Test
    public void findAirData2() {
        List<HeatmapData> airData = service.findAirData("2017012723", "PM2.5");
        assertEquals(1383, airData.size());

        for (HeatmapData heatmapData : airData) {
            if (heatmapData.getCode().equals("1001A")) {
                assertEquals(380, heatmapData.getValue());
            }
        }
    }
}
