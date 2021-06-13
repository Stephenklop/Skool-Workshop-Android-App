package com.example.skoolworkshop2.dao;

public interface FireBaseTokenDAO {
    void deleteToken(String token, int id);
    void addToken(String token, int id);
}
