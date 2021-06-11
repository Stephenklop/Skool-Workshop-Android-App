package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.NewsArticle;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NewsArticleAdapter.OnNoteListener {
    private APIDAOFactory apidaoFactory;
    private LocalAppStorage localAppStorage;
    private MenuController menuController;
    private List<Product> workshops;
    private Product cultureDay;
    private List<NewsArticle> newsArticles;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseAnalytics mFirebaseAnalytics;

    public static String adminToken;
    private static final String TAG = MainActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View root = (View) findViewById(R.id.activity_home);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        adminToken = "pK4TdR13EQfl7l5a017Jzng3QUS67qYLmiR0OvBB/szH12AZI2WQezzJS8Xlm1Z6JSrkBJJMII1F6MxV2dKP14KmL7F8y2ZDIWGlif1/wSMaR3Q9ADFG7Mv1ljXa9L/YZQH0nwVVOtQtW9FpgKLvPVHC0QCuaAH8AZQ5zvsWEBYL+9yw4HPdNA9wrI7HC1X/";

        InfoEntityManager iem = new InfoEntityManager(this.getApplication());

        View points = findViewById(R.id.activity_home_item_points);
        TextView pointsTv = points.findViewById(R.id.item_points_tv_points);
//        pointsTv.setText("Je hebt " + iem.getInfo().getPoints() + " punten");

        TextView moneyPoints = points.findViewById(R.id.item_points_tv_value);
//        moneyPoints.setText("Waarde €" + (1.00 * iem.getInfo().getPoints() * 0.03) + ",-");
        localAppStorage = new LocalAppStorage(getBaseContext());
        menuController = new MenuController(root);
        apidaoFactory = new APIDAOFactory();


        View searchPage = findViewById(R.id.activity_home_item_reservation);
        ImageView searchPageImg = searchPage.findViewById(R.id.item_dashboard_img_icon);
        TextView searchPageTv = searchPage.findViewById(R.id.item_dashboard_tv_txt);

        searchPageImg.setImageDrawable(getDrawable(R.drawable.ic_search));
        searchPageTv.setText("Zoeken");
        searchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuController.sendToSearch();
            }
        });

        View cultureDay = findViewById(R.id.activity_home_item_account);
        ImageView cultureDayImg = cultureDay.findViewById(R.id.item_dashboard_img_icon);
        TextView cultureDayTv = cultureDay.findViewById(R.id.item_dashboard_tv_txt);

        cultureDayImg.setImageDrawable(getDrawable(R.drawable.ic_sun));
        cultureDayTv.setText("Cultuurdag");
        cultureDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressInfoLayoutTestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        Button registerBtn = findViewById(R.id.activity_home_btn_register);
        Button loginBtn = findViewById(R.id.activity_home_btn_log_in);
        registerBtn.setText("Maak nu een account");
        loginBtn.setText("Log in");

        newsArticles = LocalDb.getDatabase(getBaseContext()).getNewsArticleDAO().getAllNewsArticlesOrderedByDate();

        recyclerView = findViewById(R.id.activity_home_rv_news_feed);


        mAdapter = new NewsArticleAdapter(newsArticles, MainActivity.this, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        handleNotificationData();
        getToken();
        subscribeToTopic("main");
        Bundle bundle = new Bundle();
        bundle.putString("test_event", "test_event_id");
        mFirebaseAnalytics.logEvent("eventTest", bundle);
    }

    @Override
    public void onNoteClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticles.get(position).getUrl()));
        startActivity(browserIntent);
    }

    public void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    Log.e(TAG, "Failed to get the token.");
                    return;
                }

                //get the token from task
                String token = task.getResult();

                Log.d(TAG, "Token : " + token);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to get the token : " + e.getLocalizedMessage());
            }
        });
    }

    private void handleNotificationData() {
        Bundle bundle = getIntent().getExtras();
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
                Toast.makeText(MainActivity.this, "Subscribed to " + topic, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to subscribe", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "UnSubscribed to " + topic, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to unsubscribe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}