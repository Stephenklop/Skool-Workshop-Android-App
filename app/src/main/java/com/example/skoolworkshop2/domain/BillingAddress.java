package com.example.skoolworkshop2.domain;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class BillingAddress implements Serializable {
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
    @ColumnInfo
    private String phone;
    @ColumnInfo
    private String email;
    @PrimaryKey()
    @NonNull
    @ColumnInfo
    private int id;

    public BillingAddress(String firstName, String lastName, String company, String postcode, String city, String address, String country, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.postcode = postcode;
        this.city = city;
        this.address = address;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.id = id++;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if(company.equals("") || company == null){
            return firstName + " " + lastName + '\n' + address + '\n' +
                    postcode + ' ' + city + '\n' + country ;
        } else {
            return
                    company + '\n' + firstName + " " + lastName + '\n' + address + '\n' +
                            postcode + ' ' + city + '\n' + country ;
        }
    }
}
