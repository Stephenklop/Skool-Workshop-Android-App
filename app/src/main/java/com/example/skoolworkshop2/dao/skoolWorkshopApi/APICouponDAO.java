package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.CouponDAO;
import com.example.skoolworkshop2.domain.Coupon;
import com.example.skoolworkshop2.domain.DiscountType;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APICouponDAO implements CouponDAO {
    private final String BASE_URL = "https://skool-workshop-api.herokuapp.com/api";
    private HttpsURLConnection connection;

    private void connect(String url) throws Exception {
        URL connectionUrl = new URL(url);
        connection = (HttpsURLConnection) connectionUrl.openConnection();
    }



    @Override
    public Coupon getCoupon(String coupon) {
        Coupon result = null;
        try{
            connect(BASE_URL + "/coupon/" + coupon);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                JSONObject couponJson = new JSONObject(inputLine);
                JSONObject jsonResult = couponJson.getJSONObject("result");
                if(jsonResult != null){
                    int id = jsonResult.getInt("id");
                    String code = jsonResult.getString("code");
                    double amount = Double.parseDouble(jsonResult.getString("amount"));
                    String type = jsonResult.getString("discount_type");
                    String description = jsonResult.getString("description");

                    DiscountType discountType = null;
                    if(type.equals("percent")){
                        discountType = DiscountType.PROCENTKORTING;
                    } else if(type.equals("fixed_cart")){
                        discountType = DiscountType.VASTEKORTING;
                    } else if(type.equals("fixed_product")){
                        discountType = DiscountType.PRODUCTKORTING;
                    }

                    result = new Coupon(id, code, amount, discountType, description);
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean checkCoupon(String coupon) {
        try{
            connect(BASE_URL + "/coupon/" + coupon);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicGVybWlzc2lvbiI6ImFkbWluIiwiaWF0IjoxNjIzMTQ0MTM1fQ.llvbk-9WFZdiPJvZtDfhF-08GiX114mlcGXP2PriwaY");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                JSONObject couponJson = new JSONObject(inputLine);
                JSONObject jsonResult = couponJson.getJSONObject("message");
                if(jsonResult.equals("Succes")){
                    return true;
                } else {
                    return false;
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
