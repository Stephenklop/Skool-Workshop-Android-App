package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.ShippingAddress;

import java.util.List;

public interface ShippingAddressDAO {
    @Insert()
    public void insertShippingAddress(ShippingAddress shippingAddress);

    @Query("SELECT * FROM ShippingAddress")
    public List<ShippingAddress> getAllShippingAddress();

    @Query("SELECT * FROM ShippingAddress WHERE id = :id")
    public ShippingAddress getShippingAddress(int id);
}
