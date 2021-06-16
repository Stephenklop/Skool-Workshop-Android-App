package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert()
    public void insertProducts(List<Product> products);

    @Query("SELECT * FROM Product")
    public List<Product> getAllProducts();

    @Query("SELECT * FROM Product WHERE productId = :id")
    public Product getProduct(int id);

    @Query("DELETE FROM Product")
    public void deleteAllProducts();

    @Query("SELECT 1 FROM Product WHERE name = :name")
    public int getProductIdByName(String name);

    @Query("SELECT * FROM Product WHERE productType = :type")
    public List<Product> getAllProductsByType(String type);

    @Query("SELECT * FROM Product WHERE category = :category")
    public List<Product> getAllProductsByCategory(String category);

    @Query("DELETE FROM Product")
    public void deleteProductTable();
}
