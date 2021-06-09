package com.example.skoolworkshop2.logic;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.skoolworkshop2.dao.localDatabase.LocalDb;

public class application extends Application {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        //start the db
        Room.databaseBuilder(this, LocalDb.class, "LocalDb");

        //make manager for the entity

    }
}
