package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.dao.localDatabase.entities.InfoEntity;

@Dao
public interface InfoDAO {

    @Insert
    void insertInfo(InfoEntity info);

    @Query("SELECT * FROM InfoEntity")
    InfoEntity getInfo();

    @Query("DELETE FROM InfoEntity")
    void deleteInfo();
}