package com.example.skoolworkshop2.domain;

import java.io.Serializable;
import java.util.List;

public class Workshop implements Serializable {
    private double id;
    private String name;
    private String permaLink;
    private String shortDescription;
    private String description;
    private boolean onSale;
    private Price prices;
    private List<Image> images;

    public Workshop(double id, String name, String permaLink, String shortDescription, String description, boolean onSale, Price prices, List<Image> images) {
        this.id = id;
        this.name = name;
        this.permaLink = permaLink;
        this.shortDescription = shortDescription;
        this.description = description;
        this.onSale = onSale;
        this.prices = prices;
        this.images = images;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public Price getPrices() {
        return prices;
    }

    public void setPrices(Price prices) {
        this.prices = prices;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return name;
    }
}
