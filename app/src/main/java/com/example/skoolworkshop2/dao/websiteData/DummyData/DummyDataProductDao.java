package com.example.skoolworkshop2.dao.websiteData.DummyData;

import com.example.skoolworkshop2.dao.websiteData.interfaces.ProductDao;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Workshop;

import java.util.ArrayList;

public class DummyDataProductDao implements ProductDao {
    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Product p;
        for(int i = 0; i < 10; i++){
            p = new Workshop(i, "product " + i, "This is product nr: " + i, 150.00, i + "-12-2020", 25);
            products.add(p);
        }
        return products;
    }
}
