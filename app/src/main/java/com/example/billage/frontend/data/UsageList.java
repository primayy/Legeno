package com.example.billage.frontend.data;

import com.example.billage.backend.common.Utils;

public class UsageList {
    private Integer id;
    private String date;
    private String cost;
    private String destination;
    private String time;
    private String usage_type;
    private String memo;
    private String bank_name;

    public UsageList(String date, String destination, String time, String cost, String usage_type, String bank_code, String memo, Integer id) {
        this.date = date;
        this.cost = cost;
        this.destination = destination;
        this.time=time;
        this.usage_type = usage_type;
        this.memo = memo;
        this.bank_name = Utils.bankCodeMapping(bank_code);
        this.id = id;
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
    public String getUsage_type() {return usage_type; }

    public void setUsage_type(String usage_type) { this.usage_type = usage_type; }

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
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public Integer getId() {
        return id;
    }
}
