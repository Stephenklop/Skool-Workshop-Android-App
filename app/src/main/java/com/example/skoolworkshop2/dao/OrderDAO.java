package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Order;

import org.json.JSONObject;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrders();
    List<Order> getAllOrdersOfUser(int userId);
    Order getOrder(int id);
    Order addOrder(Order order);
    boolean updateOrderStatus(int id, String status);
    Order parseJsonToOrder(JSONObject jsonObject);
    String parseOrderToJson(Order order);
}
