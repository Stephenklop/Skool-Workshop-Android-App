package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class Workshop implements Serializable {
    private double id;
    private String name;
    private String description;
//    private double price;
//    private String date; // Might be changed to Timestamp later (depends on whether that's useful)
//    private int maxParticipants;
//    private Category category;

    public Workshop(double id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
//        this.price = price;
//        this.date = date;
//        this.maxParticipants = maxParticipants;
//        this.category = category;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

//    public double getPrice() {
//        return price;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public int getMaxParticipants() {
//        return maxParticipants;
//    }

    public void setId(double id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public void setMaxParticipants(int maxParticipants) {
//        this.maxParticipants = maxParticipants;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }

    @Override
    public String toString() {
        return name;
    }
}
