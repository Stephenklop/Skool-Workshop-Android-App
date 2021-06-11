package com.example.skoolworkshop2.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.InfoEntity;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;

public class SplashScreenActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ImageView mLoadingImg = findViewById(R.id.activity_splash_screen_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) mLoadingImg.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        avd.start();


        DAOFactory apidaoFactory = new APIDAOFactory();

        Thread toMainActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    mLoadingImg.animate().alpha(0).setDuration(500).withEndAction(() -> {
                        System.out.println("THREAD 3");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }).start();
                });
            }
        });
        Thread loadProducts = new Thread(() -> {
            System.out.println("THREAD 2");
            LocalDb.getDatabase(getBaseContext()).getProductDAO().deleteProductTable();
            LocalDb.getDatabase(getBaseContext()).getProductDAO().insertProducts(apidaoFactory.getProductDAO().getAllProductsByCategory(23));
            LocalDb.getDatabase(getBaseContext()).getProductDAO().insertProducts(apidaoFactory.getProductDAO().getAllProductsByCategory(28));
            toMainActivity.start();
        });

        Thread APIThread = new Thread(() -> {
            LocalDb.getDatabase(getBaseContext()).getNewsArticleDAO().deleteNewsArticleTable();
            APIDAOFactory apiDaoFactoryNewsArticles = new APIDAOFactory();
            NewsArticleDAO newsArticleDAO = apiDaoFactoryNewsArticles.getNewsArticleDAO();
            LocalDb.getDatabase(getBaseContext()).getNewsArticleDAO().insertArticles(newsArticleDAO.getAllArticles());

            loadProducts.start();
        });

        APIThread.start();
    }
}