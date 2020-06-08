package com.example.billage.backend;

public class HowMuchPay {
    private String name;
    private double value;
    private String unit;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public HowMuchPay(String name, double value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }
}
