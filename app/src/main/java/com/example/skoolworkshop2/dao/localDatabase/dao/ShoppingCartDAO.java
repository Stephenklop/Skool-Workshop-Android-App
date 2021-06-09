package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;

import java.util.List;

@Dao
public interface ShoppingCartDAO {

    @Insert
    void insertItemInShoppingCart(ShoppingCartItem shoppingCart);

    @Query("DELETE FROM ShoppingCartItem")
    void deleteEverythingFromShoppingCart();

    @Query("DELETE FROM ShoppingCartItem WHERE id = :id")
    void deleteOneItemFromShopppingCart(int id);

    @Query("SELECT * FROM ShoppingCartItem")
    List<ShoppingCartItem> getItemsInShoppingCart();

}
