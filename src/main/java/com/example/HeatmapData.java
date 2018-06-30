package com.example;

public class HeatmapData {

    private String code;
    private int value;
    private Location location;

    HeatmapData(String code, int value, Location location) {
        this.code = code;
        this.value = value;
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }

    public float getLat() {
        return location.getLat();
    }

    public float getLng() {
        return location.getLng();
    }
}
