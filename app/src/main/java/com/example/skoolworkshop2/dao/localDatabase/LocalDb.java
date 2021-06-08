package com.example.skoolworkshop2.dao.localDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.skoolworkshop2.domain.Customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {InfoEntity.class, Customer.class}, version = 1)
public abstract class LocalDb extends RoomDatabase {

    private static volatile LocalDb INSTANCE;
    public static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(4);

    abstract public InfoDAO getInfoDAO();

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