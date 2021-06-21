package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Order;

@Dao
public interface OrderDAO {
    @Insert()
    void insertOrder(Order order);

    @Query("SELECT * FROM `Order`")
    Order getOrder();

    @Query("DELETE FROM `Order`")
    void deleteOrder();
}
