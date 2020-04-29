package com.example.billage;

public class UsageList {

    private String date;
    private String cost;
    private String destination;
    private String time;

    public UsageList(String date, String cost,String time, String destination) {
        this.date = date;
        this.cost = cost;
        this.destination = destination;
        this.time=time;
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
