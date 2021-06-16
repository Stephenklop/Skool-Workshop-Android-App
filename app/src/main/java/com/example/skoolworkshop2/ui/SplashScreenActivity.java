package com.example.skoolworkshop2.ui;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.notifications.MessagingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreenActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            System.out.println( "DATA BUNDLE" +bundle.toString());
        }


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

        MessagingService messagingService = new MessagingService();
        messagingService.handleNotificationData(getIntent());
        messagingService.subscribeToTopic("main");

        Thread tokenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserManager iem = new UserManager(getApplication());
                if(iem.hasInfo()){

                }
                APIThread.start();
            }
        });
        tokenThread.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "On New Intent called");
    }
}