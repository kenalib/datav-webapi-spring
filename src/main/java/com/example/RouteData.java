package com.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class RouteData {

    private String from;
    private String to;
    private LocalDateTime timeStump;

    RouteData(Location from, Location to) {
        this.from = from.getLngStr() + "," + from.getLatStr();
        this.to = to.getLngStr() + "," + to.getLatStr();
        timeStump = LocalDateTime.now();
    }

    String getFrom() {
        return from;
    }

    String getTo() {
        return to;
    }

    boolean isExpired(int expireMinutes) {
        LocalDateTime now = LocalDateTime.now();

        return ChronoUnit.MINUTES.between(timeStump, now) > expireMinutes;
    }
}
