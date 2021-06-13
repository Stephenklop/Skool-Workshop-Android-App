package com.example.skoolworkshop2.domain;

public class ShippingAddress {
    private String firstName;
    private String lastName;
    private String company;
    private String postcode;
    private String city;
    private String address;
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


    public String getPostcode() {
        return postcode;
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

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
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
