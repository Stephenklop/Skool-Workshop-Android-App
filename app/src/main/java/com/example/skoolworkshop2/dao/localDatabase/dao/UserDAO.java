package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.User;

@Dao
public interface UserDAO {

    @Insert
    void insertInfo(User user);

    @Query("SELECT * FROM User")
    User getInfo();

    @Query("DELETE FROM User")
    void deleteInfo();

    //Update User in SQL lite db
//    @Query("UPDATE User SET username = name)
//    void updateUser(String name);
}