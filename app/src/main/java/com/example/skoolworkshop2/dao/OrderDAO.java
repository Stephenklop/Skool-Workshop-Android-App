package com.example.skoolworkshop2.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;

import org.json.JSONObject;

import java.util.List;

@Dao
public interface OrderDAO {
    List<Reservation> getAllReservationsFromUser(int userId);
    List<Order> getAllOrders();
    List<Order> getAllOrdersOfUser(int userId);
    Order getOrder(int id);
    Order addOrder(Order order);
    boolean updateOrderStatus(int id, String status);
    Order parseJsonToOrder(JSONObject jsonObject);
    String parseOrderToJson(Order order);
}
