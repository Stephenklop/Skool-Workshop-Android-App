package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.BillingAddress;

@Dao
public interface BillingAddressDAO {
    @Insert()
    long insertBillingAddress(BillingAddress billingAddress);

    @Query("SELECT * FROM BillingAddress")
    BillingAddress getBillingAddress();

    @Query("DELETE FROM BillingAddress")
    void deleteBillingAddress();
}
