package com.example;

public class GeoData {

    private String code;
    private int value;
    private float lat;
    private float lng;

    GeoData(String code, int value, float lat, float lng) {
        this.code = code;
        this.value = value;

        this.lat = lat;
        this.lng = lng;
    }

    public String getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }
}
