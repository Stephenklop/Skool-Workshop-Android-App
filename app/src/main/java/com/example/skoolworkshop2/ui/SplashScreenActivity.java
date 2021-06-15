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



        handleNotificationData();
        getToken();

        subscribeToTopic("main");

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


    public String getToken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    Log.e(TAG, "Failed to get the token.");
                    return;
                }

                //get the token from task
                token[0] = task.getResult();

                Log.d(TAG, "Token : " + token[0]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to get the token : " + e.getLocalizedMessage());
            }
        });
        return token[0];
    }


    private void handleNotificationData() {
        Bundle bundle = getIntent().getExtras();
        System.out.println(getIntent().getExtras());
        if(bundle != null) {
            if(bundle.containsKey("data1")) {
                Log.d(TAG, "Data1: " + bundle.getString("data1"));
            }
            if(bundle.containsKey("data2")) {
                Log.d(TAG, "Data2: " + bundle.getString("data2"));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "On New Intent called");
    }

    /**
     * method to subscribe to topic
     *
     * @param topic to which subscribe
     */
    private void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(SplashScreenActivity.this, "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Subscribed to " + topic);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(SplashScreenActivity.this, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed to subscribe");
            }
        });
    }

    /**
     * method to unsubscribe to topic
     *
     * @param topic to which unsubscribe
     */
    private void unsubscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SplashScreenActivity.this, "UnSubscribed to " + topic, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SplashScreenActivity.this, "Failed to unsubscribe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}