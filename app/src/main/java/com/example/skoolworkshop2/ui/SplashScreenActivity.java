package com.example.skoolworkshop2.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;

public class SplashScreenActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        InfoEntityManager iem = new InfoEntityManager(this.getApplication());

        //insert test information into db
        iem.updateInfo(new InfoEntity("Bas Buijsen", "bbuijsen@gmail.com", "token", "1gCA&cC1ArczV(#wsd8iOmV3", 0));

        UserDAO userDAO = new APIUserDAO();

        DAOFactory apidaoFactory = new APIDAOFactory();

        Thread toMainActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("THREAD 3");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Thread loadProducts = new Thread(() -> {
            System.out.println("THREAD 2");
            System.out.println(apidaoFactory.getProductDAO().getAllProductsByCategory(23));
            System.out.println(apidaoFactory.getProductDAO().getAllProductsByCategory(28).get(0));
            toMainActivity.start();
        });
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("THREAD 1");
                InfoEntity updatedInfoEntity = iem.getInfo();
                User user = userDAO.signUserIn("bbuijsen@gmail.com", "1gCA&cC1ArczV(#wsd8iOmV3");
                updatedInfoEntity.setToken(user.getToken());
                updatedInfoEntity.setPoints(user.getPoints());
                iem.updateInfo(updatedInfoEntity);
                loadProducts.start();
            }
        });




        t.start();


    }
}