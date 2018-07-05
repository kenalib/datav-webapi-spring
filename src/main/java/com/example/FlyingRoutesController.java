package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
    List<RouteData> postJapan(
            @RequestParam(value="route-csv") String routeCsv,
            @RequestParam(value="mode") String mode,
            @RequestParam(value = "expire_minutes", required = false) Integer expireMinutes) {
        return service.saveFlyingRoutes(routeCsv, mode, expireMinutes);
    }

}
