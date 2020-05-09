package com.example.billage.backend.api.data.token;

public enum Scope {
    LOGIN("login"),
    INQUIRY("inquiry"),
    TRANSFER("transfer"),
    OOB("oob"),
    SA("sa");

    private String nameVal;

    Scope(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getNameVal() {
        return nameVal;
    }
}
