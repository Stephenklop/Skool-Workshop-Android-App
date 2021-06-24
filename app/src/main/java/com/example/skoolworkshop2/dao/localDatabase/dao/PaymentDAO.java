package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Payment;

@Dao
public interface PaymentDAO {
    @Insert()
    long addPayment(Payment payment);

    @Query("SELECT * FROM Payment")
    Payment getPayment();

    @Query("DELETE FROM Payment")
    void deletePayment();
}