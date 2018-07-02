package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flying-routes")
@Validated
public class FlyingRoutesController {

    @Autowired
    private FlyingRoutesService service;

    @CrossOrigin("*")
    @GetMapping("/japan")
    List<RouteData> getJapan() {
        return service.findFlyingRoutes();
    }

    @CrossOrigin("*")
    @PostMapping("/japan")
    List<RouteData> postJapan(@RequestParam(value="route-csv") String routeCsv) {
        return service.findFlyingRoutes(routeCsv);
    }

}
