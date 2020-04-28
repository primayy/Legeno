package com.example.billage;

public class UsageList {

    private String date;
    private String cost;
    private String destination;

    public UsageList(String date, String cost, String destination) {
        this.date = date;
        this.cost = cost;
        this.destination = destination;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
