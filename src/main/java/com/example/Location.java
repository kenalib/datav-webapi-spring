package com.example;

class Location {

    private float lat;
    private float lng;

    Location(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
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
