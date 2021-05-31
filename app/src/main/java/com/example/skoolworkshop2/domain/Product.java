package com.example.skoolworkshop2.domain;

public interface Product {
    int getId();
    String getName();
    String[] getDescription();
    double getPrice();

    void setId(int id);
    void setName(String name);
    void setDescription(String[] description);
    void setPrice(double price);
}
