package com.example.skoolworkshop2.dao;

import android.content.Context;

import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.User;

public interface UserDAO {
    User signUserIn(String username, String password);
    User registerUser(String username, String email, String password);
    Customer getLastCustomer();
    void updateUser(String email, String displayName, String firstName, String lastName);
}