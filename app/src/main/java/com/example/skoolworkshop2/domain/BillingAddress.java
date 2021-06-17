package com.example.skoolworkshop2.domain;

public class BillingAddress {
    private String firstName;
    private String lastName;
    private String company;
    private String postcode;
    private String city;
    private String address;
    private String country;
    private String phone;
    private String email;

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