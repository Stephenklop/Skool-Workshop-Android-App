package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {

    @ColumnInfo @PrimaryKey @NonNull
    private int id;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private int points;
    //private String registrationDate; // Might be changed to Timestamp later (depends on whether that's useful)

    @ForeignKey(entity = BillingAddress.class,
    parentColumns = "id",
    childColumns = "billingAddressId")
    private int billingAddressId;
    @ForeignKey(entity = ShippingAddress.class,
            parentColumns = "id",
            childColumns = "shippingAddressId")
    private int shippingAddressId;

    public User(int id, String email, String username, int points, int billingAddressId, int shippingAddressId) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.points = points;
        this.billingAddressId = billingAddressId;
        this.shippingAddressId = shippingAddressId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", points=" + points +
                ", billingAddressId=" + billingAddressId +
                ", shippingAddressId=" + shippingAddressId +
                '}';
    }
}