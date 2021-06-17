package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.BillingAddress;

import java.util.List;

public interface BillingAddressDAO {
    @Insert()
    public void insertBillingAddress(BillingAddress billingAddress);

    @Query("SELECT * FROM BillingAddress")
    public List<BillingAddress> getAllBillingAddress();

    @Query("SELECT * FROM BillingAddress WHERE id = :id")
    public BillingAddress getBillingAddress(int id);
}
