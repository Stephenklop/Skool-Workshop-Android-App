package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Order;

import java.util.List;

public interface OrderDAO {
    @Insert()
    public void insertOrder(Order order);

    @Query("SELECT * FROM Order")
    public List<Order> getAllOrders();

    @Query("SELECT * FROM Order WHERE id = :id")
    public Order getOrder(int id);
}
