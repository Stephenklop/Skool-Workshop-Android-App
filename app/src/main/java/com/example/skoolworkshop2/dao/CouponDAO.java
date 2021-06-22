package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Coupon;

public interface CouponDAO {
    Coupon getCoupon(String coupon);
    boolean checkCoupon(String coupon);
}
