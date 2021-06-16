package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.domain.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertInfo(User user);

    @Query("SELECT * FROM User")
    User getInfo();

    @Query("DELETE FROM User")
    void deleteInfo();

    // Billing

    @Query("SELECT * FROM BillingAddress WHERE billingAddress.id = :id")
    BillingAddress getBillingAddress(int id);

    @Query("SELECT * FROM BillingAddress")
    BillingAddress getAddresses();

    @Insert
    void insertBillingaddress(BillingAddress address);

    @Query("DELETE FROM billingaddress WHERE id = :id")
    void deleteAdress(int id);

    // Shipping
    @Query("SELECT * FROM ShippingAddress WHERE shippingAddress.id = :id")
    ShippingAddress getShippingAddress(int id);

    @Query("SELECT * FROM shippingaddress")
    ShippingAddress getShippingAddresses();

    @Insert
    void insertShippingAddress(ShippingAddress address);

    @Query("DELETE FROM shippingaddress WHERE id = :id")
    void deleteShippingAddress(int id);


}