package com.example;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirDataServiceTest extends TestCase {

    @Autowired
    AirDataService airDataService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void init() {
        assertNotNull(airDataService);
        airDataService.init();

        assertEquals(1497, airDataService.getCities().size());
        assertEquals(1482, airDataService.getPositions().size());
        assertEquals(39.8673F, airDataService.getPosition("1001A")[0]);
        assertEquals(116.366F, airDataService.getPosition("1001A")[1]);
        assertEquals(23.1323F, airDataService.getPosition("2846A")[0]);
        assertEquals(113.3208F, airDataService.getPosition("2846A")[1]);
    }

    @Test
    public void findAirData() {
        List<GeoData> airData = airDataService.findAirData("2017012722", "AQI");
        assertEquals(1387, airData.size());

        for (GeoData geoData: airData) {
            if (geoData.getCode().equals("1001A")) {
                assertEquals(371, geoData.getValue());
            }
        }
    }
}
