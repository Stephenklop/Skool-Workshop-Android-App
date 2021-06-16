package com.example.skoolworkshop2.domain;

import java.io.Serializable;
import java.util.List;

public class CultureDayItem implements Serializable, ProductItem {
    private Product product;
    private String date;
    private int rounds;
    private int workshopPerWorkshopRound;
    private int roundDuration;
    // TODO: Add product validation
    private List<Product> products;
    private String timeSchedule;
    private int participants;
    private int amountOfParticipantsGraffitiTshirt;
    private String learningLevel;

    public CultureDayItem(Product product) {
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

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWorkshopPerWorkshopRound() {
        return workshopPerWorkshopRound;
    }

    public void setWorkshopPerWorkshopRound(int workshopPerWorkshopRound) {
        this.workshopPerWorkshopRound = workshopPerWorkshopRound;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getAmountOfParticipantsGraffitiTshirt() {
        return amountOfParticipantsGraffitiTshirt;
    }

    public void setAmountOfParticipantsGraffitiTshirt(int amountOfParticipantsGraffitiTshirt) {
        this.amountOfParticipantsGraffitiTshirt = amountOfParticipantsGraffitiTshirt;
    }

    public String getLearningLevel() {
        return learningLevel;
    }

    public void setLearningLevel(String learningLevel) {
        this.learningLevel = learningLevel;
    }

    public double getPrice() {
        return 2.325 * roundDuration * rounds * workshopPerWorkshopRound;
    }

    @Override
    public String toString() {
        return "CultureDayItem{" +
                "product=" + product +
                ", date='" + date + '\'' +
                ", rounds=" + rounds +
                ", workshopPerWorkshopRound=" + workshopPerWorkshopRound +
                ", roundDuration=" + roundDuration +
                ", products=" + products +
                ", timeSchedule='" + timeSchedule + '\'' +
                ", participants=" + participants +
                ", amountOfParticipantsGraffitiTshirt=" + amountOfParticipantsGraffitiTshirt +
                ", learningLevel='" + learningLevel + '\'' +
                '}';
    }
}
