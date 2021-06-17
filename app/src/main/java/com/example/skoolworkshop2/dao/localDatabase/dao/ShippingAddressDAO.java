package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.ShippingAddress;

import java.util.List;

@Dao
public interface ShippingAddressDAO {
    @Insert()
    void insertShippingAddress(ShippingAddress shippingAddress);

    @Query("SELECT * FROM ShippingAddress")
    List<ShippingAddress> getAllShippingAddress();

    @Query("SELECT * FROM ShippingAddress WHERE id = :id")
    ShippingAddress getShippingAddress(int id);
}