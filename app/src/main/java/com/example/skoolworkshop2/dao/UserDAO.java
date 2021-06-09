package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.User;

public interface UserDAO {
    User signUserIn(String username, String password);
    User registerUser(String username, String email, String password);
}