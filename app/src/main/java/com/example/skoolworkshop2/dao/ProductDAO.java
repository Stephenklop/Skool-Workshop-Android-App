package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Product;

import org.json.JSONObject;

import java.util.List;

public interface ProductDAO {
    List<Product> getAllProducts();
    List<Product> getAllProductsByCategory(int id);
    Product getProduct(int id);
    Product parseProduct(JSONObject jsonObject);
}