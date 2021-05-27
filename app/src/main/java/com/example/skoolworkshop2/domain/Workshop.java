package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class Workshop implements Product, Serializable {
    private int id;
    private String name;
    private Category category;
    private String[] description; // The first item is the overview, followed by: the content, practical info & costs
    private double price;
    private String date; // Might be changed to Timestamp later (depends on whether that's useful)
    private int duration; // Workshop duration in minutes
    private int maxParticipants;

    public Workshop(int id, String name, Category category, String[] description, double price, String date, int duration, int maxParticipants) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.date = date;
        this.duration = duration;
        this.maxParticipants = maxParticipants;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String[] getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void setDescription(String[] description) {
        this.description = description;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}