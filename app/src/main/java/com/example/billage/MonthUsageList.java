package com.example.billage;

public class MonthUsageList {
    private int count;
    private String cost;
    private String usage_type;

    public MonthUsageList(int count, int month, String cost, String usage_type) {
        this.count = count;
        this.cost = cost;
        this.usage_type = usage_type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

}
