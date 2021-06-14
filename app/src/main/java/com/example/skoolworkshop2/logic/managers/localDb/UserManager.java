package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.localDatabase.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;

import java.util.List;

public class UserManager {
    private LocalDb localDb;
    private UserDAO userDAO;
    private String TAG = getClass().getSimpleName();

    public UserManager(Application application){
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public BillingAddress getBillingAddress(int id){
        Log.d(TAG, "getBillingAddress: id :" + id);
        return userDAO.getBillingAddress(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BillingAddress> getAddresses(){
        return userDAO.getAddresses();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertBillingaddress(BillingAddress address){
        userDAO.insertBillingaddress(address);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteAdress(){
        userDAO.deleteAdress();
    }

}
