package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.localDatabase.dao.CustomerDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;

public class UserManager {
    private LocalDb localDb;
    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    public UserManager(Application application){
        localDb = LocalDb.getDatabase(application);
        userDAO = localDb.getUserDAO();
        customerDAO = localDb.getCustomerDAO();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User getInfo(){

        User user = userDAO.getInfo();

        return userDAO.getInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertInfo(User user){
        userDAO.deleteInfo();
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

    public Customer getCustomer(){
        return customerDAO.getCustomer();
    }
}
