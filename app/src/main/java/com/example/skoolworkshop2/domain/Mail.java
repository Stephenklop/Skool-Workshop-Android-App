package com.example.skoolworkshop2.domain;

public class Mail {
    private int amountOfPerson;
    private String date;
    private String time;
    private String location;
    private int cjp;
    private String email;
    private String phone;
    private String message;

    public Mail(int amountOfPerson, String date, String time, String location, int cjp, String email, String phone, String message) {
        this.amountOfPerson = amountOfPerson;
        this.date = date;
        this.time = time;
        this.location = location;
        this.cjp = cjp;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public int getAmountOfPerson() {
        return amountOfPerson;
    }

    public void setAmountOfPerson(int amountOfPerson) {
        this.amountOfPerson = amountOfPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCjp() {
        return cjp;
    }

    public void setCjp(int cjp) {
        this.cjp = cjp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
