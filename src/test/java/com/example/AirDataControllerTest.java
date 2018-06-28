package com.example;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirDataControllerTest extends TestCase {
    // c.f. https://qiita.com/mitsuya/items/be50dc329b4f3abe5ac5

    @Mock
    private AirDataService airDataService;

    @InjectMocks
    private AirDataController airDataController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void airData() {
        List<GeoData> geoDataList = new ArrayList<>();
        GeoData geoData = new GeoData("1001A", 371 , 116.366F, 39.8673F);
        geoDataList.add(geoData);

        when(airDataService.findAirData(anyString(), anyString()))
                .thenReturn(geoDataList);

        List<GeoData> actual = airDataController.airData("AQI", "2017012722");

        verify(airDataService, times(1))
                .findAirData("2017012722", "AQI");

        assertThat(actual, is(geoDataList));
    }
}
