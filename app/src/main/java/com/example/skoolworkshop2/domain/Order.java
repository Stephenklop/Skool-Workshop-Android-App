package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @ColumnInfo @PrimaryKey(autoGenerate = true) @NonNull
    private int id;

    @ColumnInfo
    private String status;

    @ColumnInfo
    private int customerId;

    @ColumnInfo
    private int billingAddressId;

    @ColumnInfo
    private int shippingAddressId;

    @ColumnInfo
    private String paymentMethod;

    @ColumnInfo
    private String paymentMethodTitle;

    @ColumnInfo
    private String customerNote;

    @ColumnInfo
    private String billingCJP;

    @ColumnInfo
    private String billingVideo;

    @ColumnInfo
    private String reservationSystem;

    @ColumnInfo
    private double distance;

    @ColumnInfo
    private double orderPrice;

    @ColumnInfo
    private double travelCosts;

    @ColumnInfo
    private double price;

    public Order(String status, int customerId, int billingAddressId, int shippingAddressId, String paymentMethod, String paymentMethodTitle, String customerNote, String billingCJP, String billingVideo, String reservationSystem, double distance, double orderPrice) {
        this.status = status;
        this.customerId = customerId;
        this.billingAddressId = billingAddressId;
        this.shippingAddressId = shippingAddressId;
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.customerNote = customerNote;
        this.billingCJP = billingCJP;
        this.billingVideo = billingVideo;
        this.reservationSystem = reservationSystem;
        this.distance = distance;
        this.orderPrice = orderPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getBillingCJP() {
        return billingCJP;
    }

    public void setBillingCJP(String billingCJP) {
        this.billingCJP = billingCJP;
    }

    public String getBillingVideo() {
        return billingVideo;
    }

    public void setBillingVideo(String billingVideo) {
        this.billingVideo = billingVideo;
    }

    public String getReservationSystem() {
        return reservationSystem;
    }

    public void setReservationSystem(String reservationSystem) {
        this.reservationSystem = reservationSystem;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getTravelCosts() {
        return Math.round(distance * 0.56 * 100.0) / 100.0;
    }

    public void setTravelCosts(double travelCosts) {
        this.travelCosts = travelCosts;
    }

    public double getPrice() {
        return orderPrice + travelCosts;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", customerId=" + customerId +
                ", billingAddressId=" + billingAddressId +
                ", shippingAddressId=" + shippingAddressId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentMethodTitle='" + paymentMethodTitle + '\'' +
                ", customerNote='" + customerNote + '\'' +
                ", billingCJP=" + billingCJP +
                ", billingVideo='" + billingVideo + '\'' +
                ", reservationSystem='" + reservationSystem + '\'' +
                ", distance=" + distance +
                ", price=" + price +
                '}';
    }
}