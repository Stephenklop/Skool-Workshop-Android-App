package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Bank;

import java.util.List;

@Dao
public interface BankDAO {

    @Insert()
    void insertAllBanks(List<Bank> banks);

    @Query("SELECT * FROM Bank")
    List<Bank> getAllBanks();

    @Query("SELECT * FROM Bank WHERE id = :id")
    Bank getBank(int id);

    @Query("DELETE FROM Bank")
    void deleteAllBanks();
}
