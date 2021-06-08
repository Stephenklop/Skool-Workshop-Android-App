package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.skoolworkshop2.dao.localDatabase.InfoDAO;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
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
        return infoDAO.getInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertInfo(InfoEntity infoEntity){
        infoEntity.setPassword(EncryptionLogic.encrypt(infoEntity.getPassword(), "secretKey"));
        infoDAO.insertInfo(infoEntity);
    }

    public boolean hasInfo(){
        return infoDAO.getInfo() != null;
    }

    public void updateInfo(InfoEntity updatedInfoEntity){
        infoDAO.deleteInfo();
        infoDAO.insertInfo(updatedInfoEntity);
    }
}
