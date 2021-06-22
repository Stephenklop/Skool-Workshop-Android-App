package com.example.skoolworkshop2.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"discountType"},
        unique = true)})
public class Coupon {
    @ColumnInfo @PrimaryKey
    private int id;

    @ColumnInfo
    private String code;

    @ColumnInfo
    private double amount;

    @ColumnInfo
    private String discountType;

    @ColumnInfo
    private String description;

    public Coupon(int id, String code, double amount, String discountType, String description) {
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

    public DiscountType getDiscountTypeEnum() {
        if(discountType.equals("percent")){
            return DiscountType.PROCENTKORTING;
        } else if(discountType.equals("fixed_cart")){
            return DiscountType.VASTEKORTING;
        } else if(discountType.equals("fixed_product")){
            return DiscountType.PRODUCTKORTING;
        } else if(discountType.equals("points")){
            return DiscountType.POINTS;
        } else {
            return null;
        }
    }

    public String getDiscountType(){
        return this.discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
