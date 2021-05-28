package com.example.skoolworkshop2.domain;

import java.io.Serializable;
import java.util.List;

public class CultureDay implements Product, Serializable {
    private int id;
    private String name;
    private String[] description;
    private List<Workshop> workshops;
    private int rounds;
    private double price;
    private String date; // Might be changed to Timestamp later (depends on whether that's useful)
    private int maxParticipants;

    public CultureDay(int id, String name, String[] description, List<Workshop> workshops, int rounds, double price, String date, int maxParticipants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.workshops = workshops;
        this.rounds = rounds;
        this.price = price;
        this.date = date;
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

    @Override
    public String[] getDescription() {
        return description;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public int getRounds() {
        return rounds;
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

    public void setDescription(String[] description) {
        this.description = description;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
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
}