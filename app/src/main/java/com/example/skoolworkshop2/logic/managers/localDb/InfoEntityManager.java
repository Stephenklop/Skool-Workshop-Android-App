package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.localDatabase.dao.InfoDAO;
import com.example.skoolworkshop2.dao.localDatabase.entities.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;

public class InfoEntityManager {
    private LocalDb localDb;
    private InfoDAO infoDAO;

    public InfoEntityManager(Application application){
        localDb = LocalDb.getDatabase(application);
        infoDAO = localDb.getInfoDAO();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public InfoEntity getInfo(){
        InfoEntity ie = infoDAO.getInfo();
        ie.setPassword(EncryptionLogic.decrypt(ie.getPassword(), "secretKey"));
        ie.setToken(EncryptionLogic.decrypt(ie.getToken(), "secretKey"));
        ie.setEmail(EncryptionLogic.decrypt(ie.getEmail(), "secretKey"));
        return infoDAO.getInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertInfo(InfoEntity infoEntity){
        infoEntity.setPassword(EncryptionLogic.encrypt(infoEntity.getPassword(), "secretKey"));
        infoEntity.setToken(EncryptionLogic.encrypt(infoEntity.getToken(), "secretKey"));
        infoEntity.setEmail(EncryptionLogic.encrypt(infoEntity.getEmail(), "secretKey"));
        infoDAO.insertInfo(infoEntity);
    }

    public boolean hasInfo(){
        return infoDAO.getInfo() != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateInfo(InfoEntity updatedInfoEntity){
        infoDAO.deleteInfo();
        updatedInfoEntity.setPassword(EncryptionLogic.encrypt(updatedInfoEntity.getPassword(), "secretKey"));
        updatedInfoEntity.setToken(EncryptionLogic.encrypt(updatedInfoEntity.getToken(), "secretKey"));
        updatedInfoEntity.setEmail(EncryptionLogic.encrypt(updatedInfoEntity.getEmail(), "secretKey"));
        infoDAO.insertInfo(updatedInfoEntity);
    }
}
