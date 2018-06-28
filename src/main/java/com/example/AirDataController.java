package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/air-data")
@Validated
public class AirDataController {
    private static final String origin = "*";
    private static final String patternType =
            "aqi|pm2.5|pm2.5_24h|pm10|pm10_24h|so2|so2_24h|"
                    + "no2|no2_24h|o3|o3_24h|o3_8h|o3_8h_24h|co|co_24h";

    @Autowired
    private AirDataService service;

    @CrossOrigin("*")
    @RequestMapping("/{type}")
    public List<GeoData> airData(
            @Valid @Pattern(regexp = patternType)
            @PathVariable("type") String type,
            @Valid @Pattern(regexp = "^[0-9]{10}")
            @RequestParam(value="date", defaultValue = "2017012722") String dateHour) {

        return service.findAirData(dateHour, type.toUpperCase());
    }
}
