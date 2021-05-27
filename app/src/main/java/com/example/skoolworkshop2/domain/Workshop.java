package com.example.skoolworkshop2.domain;

public class Workshop implements Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String date; // Might be changed to Timestamp later (depends on whether that's useful)
    private int maxParticipants;
    private String category;

    public Workshop(int id, String name, String description, double price, String date, int maxParticipants, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.maxParticipants = maxParticipants;
        this.category = category;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}