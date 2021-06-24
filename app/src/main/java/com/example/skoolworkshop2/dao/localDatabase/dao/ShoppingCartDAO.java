package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;

import java.util.List;

@Dao
public interface ShoppingCartDAO {

    @Insert
    long insertItemInShoppingCart(ShoppingCartItem shoppingCart);

    @Query("DELETE FROM ShoppingCartItem")
    void deleteEverythingFromShoppingCart();

    @Query("DELETE FROM ShoppingCartItem WHERE id = :id")
    void deleteOneItemFromShoppingCart(int id);

    @Query("SELECT * FROM ShoppingCartItem")
    List<ShoppingCartItem> getItemsInShoppingCart();

    @Query("SELECT SUM(price) FROM ShoppingCartItem")
    double getTotalShoppingCartPrice();

    @Query("SELECT COUNT(id) FROM ShoppingCartItem")
    int getAmountOfShoppingCartItems();

    @Query("UPDATE ShoppingCartItem SET price = :price WHERE productId = :productId")
    void updateShoppingCartItemPrice(int productId, double price);
}
