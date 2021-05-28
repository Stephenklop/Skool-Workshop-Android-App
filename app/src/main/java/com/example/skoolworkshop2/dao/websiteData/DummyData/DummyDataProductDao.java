package com.example.skoolworkshop2.dao.websiteData.DummyData;

import com.example.skoolworkshop2.dao.websiteData.interfaces.ProductDao;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Workshop;

import java.util.ArrayList;

public class DummyDataProductDao implements ProductDao {
    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Product p;
        for(int i = 0; i < 10; i++){
            String[] desc = {"This is product nr: " + i, "This is product nr: " + i, "This is product nr: " + i, "This is product nr: " + i};
            p = new Workshop(i, "product ", new String[]{"Test"}, 55.55, "11-11-2020", 1, Category.DS);
            products.add(p);
        }
        return products;
    }
}
