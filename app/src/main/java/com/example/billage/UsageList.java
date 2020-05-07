package com.example.billage;

import android.util.Log;

import java.util.ArrayList;

public class UsageList {

    private String date;
    private String cost;
    private String destination;
    private String time;
    private boolean usage_type = false;

    public UsageList(String date, String destination,String time, String cost,boolean usage_type) {
        this.date = date;
        this.cost = cost;
        this.destination = destination;
        this.time=time;
        this.usage_type = usage_type;
    }

    public String getDate() {
        return date;
    }

    public String getCost() {
        return cost;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() { return time; }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTime(String time) { this.time = time; }

}
