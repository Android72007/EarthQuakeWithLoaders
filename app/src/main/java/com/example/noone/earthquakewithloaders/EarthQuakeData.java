package com.example.noone.earthquakewithloaders;

/**
 * Created by No One on 3/24/2017.
 */

public class EarthQuakeData {

    private double magnitude;
    private String place;
    private String time;
    private String url;
    public EarthQuakeData(double magnitude, String place, String time, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.time = time;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
