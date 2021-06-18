package com.example.skoolworkshop2.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM Reservation WHERE costumerId = :userId")
    List<Reservation> getAllReservationsFromUser(int userId);
}
