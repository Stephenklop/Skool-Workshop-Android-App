package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Workshop;

import org.json.JSONObject;

import java.util.List;

public interface ProductDAO {
    List<Workshop> getAllProducts();
    List<Workshop> getAllProductsByCategory(int id);
    Workshop getProduct(int id);
    Workshop parseWorkshop(JSONObject jsonObject);
}