package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.localDatabase.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;

public class InfoEntityManager {
    private LocalDb localDb;
    private UserDAO userDAO;

    public InfoEntityManager(Application application){
        localDb = LocalDb.getDatabase(application);
        userDAO = localDb.getUserDAO();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User getInfo(){

        User user = userDAO.getInfo();

        return userDAO.getInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertInfo(User user){
        userDAO.insertInfo(user);
    }

    public boolean hasInfo(){
        return userDAO.getInfo() != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateInfo(User user){
        userDAO.deleteInfo();
        userDAO.insertInfo(user);
    }
}
