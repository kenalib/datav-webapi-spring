package com.example;

public class RouteData {

    private String from;
    private String to;

    RouteData(Location from, Location to) {
        this.from = from.getLngStr() + "," + from.getLatStr();
        this.to = to.getLngStr() + "," + to.getLatStr();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
