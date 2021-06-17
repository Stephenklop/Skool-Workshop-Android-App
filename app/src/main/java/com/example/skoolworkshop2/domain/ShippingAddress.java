package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShippingAddress {
    @ColumnInfo @PrimaryKey(autoGenerate = true) @NonNull
    private int id;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String company;

    @ColumnInfo
    private String postcode;

    @ColumnInfo
    private String city;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private String country;

    public ShippingAddress(String firstName, String lastName, String company, String postcode, String city, String address, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.postcode = postcode;
        this.city = city;
        this.address = address;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return
                company + '\n' + firstName + " " + lastName + '\n' +
                        address + '\n' +
                        postcode + '\n' +
                        city + '\n' +
                        country + '\n';
    }
}
