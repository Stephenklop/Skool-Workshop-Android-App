package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Customer;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM Customer")
    Customer getCustomer();

    @Insert()
    void addCustomer(Customer customer);

    @Query("DELETE FROM Customer")
    void deleteCustomer();
}
