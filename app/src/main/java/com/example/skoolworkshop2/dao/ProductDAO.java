package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Workshop;

import java.util.List;

public interface ProductDAO {
    List<Workshop> getAllProducts();
    Workshop getProduct(int id);
}