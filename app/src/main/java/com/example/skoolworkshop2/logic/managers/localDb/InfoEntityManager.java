package com.example.skoolworkshop2.logic.managers.localDb;

import android.app.Application;

import com.example.skoolworkshop2.dao.localDatabase.InfoDAO;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;

public class InfoEntityManager {
    private LocalDb localDb;
    private InfoDAO infoDAO;

    public InfoEntityManager(Application application){
        localDb = LocalDb.getDatabase(application);
        infoDAO = localDb.getInfoDAO();
    }

    public InfoEntity getInfo(){
        return infoDAO.getInfo();
    }

    public void insertInfo(InfoEntity infoEntity){
        infoEntity.setPassword(infoEntity.getPassword());
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
