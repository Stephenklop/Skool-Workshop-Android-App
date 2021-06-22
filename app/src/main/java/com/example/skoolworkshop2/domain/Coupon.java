package com.example.skoolworkshop2.domain;

public class Coupon {
    private int id;
    private String code;
    private double amount;
    private DiscountType discountType;
    private String description;

    public Coupon(int id, String code, double amount, DiscountType discountType, String description) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.discountType = discountType;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
