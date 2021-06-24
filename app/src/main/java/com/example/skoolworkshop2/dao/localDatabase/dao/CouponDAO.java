package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Coupon;

import java.util.List;

@Dao
public interface CouponDAO {

    @Insert
    void insertCoupon(Coupon coupon);

    @Query("SELECT * FROM Coupon")
    List<Coupon> getAllCoupons();

    @Query("DELETE FROM Coupon WHERE id = :id")
    void deleteCoupon(int id);

    @Query("DELETE FROM Coupon")
    void deleteAllCoupons();

    @Query("SELECT * FROM Coupon WHERE discountType = 'points'")
    Coupon getPointsCoupon();
}