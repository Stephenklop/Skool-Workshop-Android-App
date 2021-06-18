package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrdersFromUser(int userId);
}
