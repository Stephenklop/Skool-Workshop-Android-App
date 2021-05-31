package com.example.skoolworkshop2.domain;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private List<Product> products;
    private Customer customer;
    private String orderDate; // Might be changed to Timestamp later (depends on whether that's useful)
    private int totalPrice;

    public Order(List<Product> products, Customer customer, String orderDate, int totalPrice) {
        this.products = products;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}