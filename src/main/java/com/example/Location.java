package com.example;

class Location {

    private float lat;
    private float lng;

    Location(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    Location(String latStr, String lngStr) {
        this.lat = Float.parseFloat(latStr);
        this.lng = Float.parseFloat(lngStr);
    }

    float getLat() {
        return lat;
    }

    float getLng() {
        return lng;
    }

    String getLatStr() {
        return Float.toString(lat);
    }

    String getLngStr() {
        return Float.toString(lng);
    }

}
