package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class Price implements Serializable {
    private double price;
    private double regularPrice;
    private double salePrice;

    public Price(double price, double regularPrice, double salePrice) {
        this.price = price;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
