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
    private static String tokyo = "139.69167,35.68944";
    private static String osaka = "135.52,34.68639";
    private static String fukuoka = "130.41806,33.60639";

    @Autowired
    private FlyingRoutesService service;

    @Before
    public void setUp() {
        assertNotNull(service);

        assertEquals(282, service.getCities().size());
        assertEquals(35.68944F, service.getCities().get("東京").getLat());
        assertEquals(139.69167F, service.getCities().get("東京").getLng());

        service.saveFlyingRoutes("東京,大阪", "flush");
        service.saveFlyingRoutes("東京,福岡", "append");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findFlyingRoutes() {
        List<RouteData> routes = service.findFlyingRoutes();

        assertEquals(2, routes.size());
        assertEquals(tokyo, routes.get(0).getFrom());
        assertEquals(osaka, routes.get(0).getTo());
        assertEquals(tokyo, routes.get(1).getFrom());
        assertEquals(fukuoka, routes.get(1).getTo());
    }

    @Test
    public void flushFlyingRoutes() {
        service.saveFlyingRoutes("東京,福岡", "flush");

        List<RouteData> routes = service.findFlyingRoutes();

        assertEquals(1, routes.size());
        assertNotEquals(osaka, routes.get(0).getTo());
        assertEquals(tokyo, routes.get(0).getFrom());
        assertEquals(fukuoka, routes.get(0).getTo());
    }
}
