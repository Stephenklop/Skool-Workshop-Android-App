package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.ShippingAddress;

@Dao
public interface ShippingAddressDAO {
    @Insert()
    void insertShippingAddress(ShippingAddress shippingAddress);

    @Query("SELECT * FROM ShippingAddress")
    ShippingAddress getShippingAddress();

    @Query("DELETE FROM ShippingAddress")
    void deleteShippingAddress();
}