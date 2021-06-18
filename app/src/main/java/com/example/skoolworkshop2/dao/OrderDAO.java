package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrders();
    List<Order> getAllOrdersOfUser(int userId);
    Order getOrder(int id);
    void addOrder(Order order);
    String parseOrderToJson(Order order);
}
