package com.example.skoolworkshop2.logic;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;

public class application extends Application {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        //start the db
        Room.databaseBuilder(this, LocalDb.class, "LocalDb");

        //make manager for the entity
        InfoEntityManager iem = new InfoEntityManager(this);

        //insert test information into db
        iem.updateInfo(new InfoEntity("Bas Buijsen", "bbuijsen@gmail.com", "token", "1gCA&cC1ArczV(#wsd8iOmV3"));

        UserDAO userDAO = new APIUserDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                InfoEntity updatedInfoEntity = iem.getInfo();
                User user = userDAO.signUserIn(iem.getInfo().getEmail(), iem.getInfo().getPassword());
                updatedInfoEntity.setToken(user.getToken());
                iem.updateInfo(updatedInfoEntity);
            }
        }).start();
    }
}
