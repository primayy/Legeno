package com.example.billage.frontend.data;

import com.example.billage.backend.common.Utils;

public class QuestList {

    private int id;
    private String name;
    private String description;
    private String complete;
    private String reward;
    private String type;


    public QuestList(int id, String name, String description, String complete, String reward, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.complete = complete;
        this.reward = reward;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}