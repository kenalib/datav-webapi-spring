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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlyingRoutesServiceTest extends TestCase {

    @Autowired
    FlyingRoutesService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() {
        assertNotNull(service);
        service.init();

        assertEquals(160, service.getCities().size());
        assertEquals(35.68944F, service.getCities().get("東京").getLat());
        assertEquals(139.69167F, service.getCities().get("東京").getLng());
    }

    @Test
    public void findFlyingRoutes() {
        service.saveFlyingRoutes("東京,大阪", "flush");

        List<RouteData> routes = service.findFlyingRoutes();

        assertEquals(1, routes.size());
        assertEquals("139.69167,35.68944", routes.get(0).getFrom());
        assertEquals("135.52,34.68639", routes.get(0).getTo());
    }

    @Test
    public void saveFlyingRoutes() {
        service.saveFlyingRoutes("東京,大阪", "flush");
        service.saveFlyingRoutes("東京,福岡", "append");

        List<RouteData> routes = service.findFlyingRoutes();

        assertEquals(2, routes.size());
        assertEquals("139.69167,35.68944", routes.get(1).getFrom());
        assertEquals("130.41806,33.60639", routes.get(1).getTo());
    }
}
