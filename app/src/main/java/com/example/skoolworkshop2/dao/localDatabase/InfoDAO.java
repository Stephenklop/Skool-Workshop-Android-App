package com.example.skoolworkshop2.dao.localDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface InfoDAO {

    @Insert
    void insertInfo(InfoEntity info);

    @Query("SELECT * FROM InfoEntity")
    InfoEntity getInfo();

    @Query("DELETE FROM InfoEntity")
    void deleteInfo();
}
