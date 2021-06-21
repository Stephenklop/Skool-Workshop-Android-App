package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class WorkshopItem implements Serializable, ProductItem {
    private Product product;
    private String date;
    private int participants;
    private int rounds;
    private int roundDuration;
    private String timeSchedule;
    private String learningLevel;

    public WorkshopItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public String getLearningLevel() {
        return learningLevel;
    }

    public void setLearningLevel(String learningLevel) {
        this.learningLevel = learningLevel;
    }

    public double getPrice() {
        double result;

        if (product.getName().toLowerCase().contains("graffiti") || product.getName().toLowerCase().contains("t-shirt")) {
            result = 25 + (participants * 7.50) + ((roundDuration * rounds) * 2.50);
        } else {
            result = 25 + (roundDuration * rounds) * 2.50;
        }

        return result;
    }

    @Override
    public String toString() {
        return "WorkshopItem{" +
                "product=" + product +
                ", date='" + date + '\'' +
                ", participants=" + participants +
                ", rounds=" + rounds +
                ", roundDuration=" + roundDuration +
                ", timeSchedule='" + timeSchedule + '\'' +
                ", learningLevel='" + learningLevel + '\'' +
                '}';
    }
}
