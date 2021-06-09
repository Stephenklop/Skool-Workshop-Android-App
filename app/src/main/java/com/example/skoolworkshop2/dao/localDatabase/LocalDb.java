package com.example.skoolworkshop2.dao.localDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.skoolworkshop2.dao.localDatabase.dao.InfoDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.ShoppingCartDAO;
import com.example.skoolworkshop2.dao.localDatabase.entities.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {InfoEntity.class, Customer.class, Product.class, Quiz.class, Bank.class, ShoppingCartItem.class}, version = 1)
public abstract class LocalDb extends RoomDatabase {

    private static volatile LocalDb INSTANCE;
    public static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(4);

    abstract public InfoDAO getInfoDAO();
    abstract public ShoppingCartDAO getShoppingCartDAO();

    public static LocalDb getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (LocalDb.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDb.class, "localDatabase").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}