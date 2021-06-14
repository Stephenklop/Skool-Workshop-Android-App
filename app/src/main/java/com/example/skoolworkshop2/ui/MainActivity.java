package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.NewsArticle;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.encryption.EncryptionLogic;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.notifications.MessagingService;
import com.example.skoolworkshop2.ui.User.AccountActivity;
import com.example.skoolworkshop2.ui.User.RegisterActivity;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;
import com.example.skoolworkshop2.ui.notifications.NotificationsActivity;
import com.example.skoolworkshop2.ui.workshop.WorkshopActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

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
    public static String androidToken = "pK4TdR13EQfl7l5a017Jzng3QUS67qYLmiR0OvBB/szH12AZI2WQezzJS8Xlm1Z6JSrkBJJMII1F6MxV2dKP14KmL7F8y2ZDIWGlif1/wSMaR3Q9ADFG7Mv1ljXa9L/YZQH0nwVVOtQtW9FpgKLvPVHC0QCuaAH8AZQ5zvsWEBYL+9yw4HPdNA9wrI7HC1X/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View root = (View) findViewById(R.id.activity_home);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        View v = findViewById(R.id.activity_home_fragment_notifications);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        if(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNewNotifications().size() > 0){
            ImageView notificationsIcon = v.findViewById(R.id.component_notifications_img_bell);
            notificationsIcon.setImageResource(R.drawable.ic_bell_on);
        }


        View include = findViewById(R.id.include);
        include.setClipToOutline(true);
        ImageView headerGradient = include.findViewById(R.id.component_home_banner_img_gradient);
        headerGradient.setClipToOutline(true);
        ImageView headerImage = include.findViewById(R.id.component_home_banner_img_base);
        headerImage.setClipToOutline(true);

        adminToken = "pK4TdR13EQfl7l5a017Jzng3QUS67qYLmiR0OvBB/szH12AZI2WQezzJS8Xlm1Z6JSrkBJJMII1F6MxV2dKP14KmL7F8y2ZDIWGlif1/wSMaR3Q9ADFG7Mv1ljXa9L/YZQH0nwVVOtQtW9FpgKLvPVHC0QCuaAH8AZQ5zvsWEBYL+9yw4HPdNA9wrI7HC1X/";

        EncryptionLogic.decrypt(androidToken, "secretKey");

        UserManager iem = new UserManager(this.getApplication());

        View points = findViewById(R.id.activity_home_item_points);
        TextView pointsTv = points.findViewById(R.id.item_points_tv_points);

        TextView greeting = findViewById(R.id.activity_home_tv_greeting);
        greeting.setText("Goedendag");

        if (iem.hasInfo()) {
            LinearLayout noAccount = findViewById(R.id.activity_home_ll_portal_msg);
            noAccount.setVisibility(View.GONE);

            LinearLayout notifications = findViewById(R.id.activity_home_ll_notifications);
            notifications.setVisibility(View.VISIBLE);

            points.setVisibility(View.VISIBLE);

            if (!iem.getCustomer().getFirstName().isEmpty()) {
                greeting.setText("Goedendag " + iem.getCustomer().getFirstName());
            } else {
                greeting.setText("Goedendag " + iem.getInfo().getUsername());
            }


            String pointsStrStart = "Je hebt ";
            String pointsStr = pointsStrStart + iem.getInfo().getPoints() + " punten";
            Spannable pointsSpannable = new SpannableString(pointsStr);
            pointsSpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)),
                    pointsStrStart.length(),
                    pointsStrStart.length() + String.valueOf(iem.getInfo().getPoints()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            pointsSpannable.setSpan(new RelativeSizeSpan(1.2f),
                    pointsStrStart.length(),
                    pointsStrStart.length() + String.valueOf(iem.getInfo().getPoints()).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            pointsTv.setText(pointsSpannable, TextView.BufferType.SPANNABLE);

            TextView moneyPoints = points.findViewById(R.id.item_points_tv_value);

            String moneyStrStart = "Waarde ";
            String moneyStr = moneyStrStart + "€" + (1.00 * iem.getInfo().getPoints() * 0.03) + ",-";
            Spannable moneySpannable = new SpannableString(moneyStr);
            moneySpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)),
                    moneyStrStart.length(),
                    moneyStrStart.length() + ("€" + (1.00 * iem.getInfo().getPoints() * 0.03) + ",-").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            moneySpannable.setSpan(new RelativeSizeSpan(1.2f),
                    moneyStrStart.length(),
                    moneyStrStart.length() + ("€" + (1.00 * iem.getInfo().getPoints() * 0.03) + ",-").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            moneyPoints.setText(moneySpannable, TextView.BufferType.SPANNABLE);
        }
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
                startActivity(new Intent(getApplicationContext(), WorkshopActivity.class));
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
                Intent intent = new Intent(getApplicationContext(), CulturedayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        Button registerBtn = findViewById(R.id.activity_home_btn_register);
        Button loginBtn = findViewById(R.id.activity_home_btn_log_in);
        registerBtn.setText("Maak nu een account");
        loginBtn.setText("Log in");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        newsArticles = LocalDb.getDatabase(getBaseContext()).getNewsArticleDAO().getAllNewsArticlesOrderedByDate();

        recyclerView = findViewById(R.id.activity_home_rv_news_feed);


        mAdapter = new NewsArticleAdapter(newsArticles, MainActivity.this, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        handleNotificationData();
//        getToken();
//
//        subscribeToTopic("main");
        Bundle bundle = new Bundle();
        bundle.putString("test_event", "test_event_id");
        mFirebaseAnalytics.logEvent("eventTest", bundle);

        Bundle loginEvent = new Bundle();
        loginEvent.putString("login_event_id", "login_event_id");
        mFirebaseAnalytics.logEvent("login_event", loginEvent);

        Bundle appOpenEvent = new Bundle();
        appOpenEvent.putString("app_open_event_id", "app_open_event_id");
        mFirebaseAnalytics.logEvent("app_open_event", appOpenEvent);

        Bundle ordersEvent = new Bundle();
        ordersEvent.putString("orders_event_id", "orders_event_id");
        mFirebaseAnalytics.logEvent("orders_event", ordersEvent);

    }

    @Override
    public void onNoteClick(int position) {
        Intent appBrowserIntent = new Intent(getApplicationContext(), WebViewActivity.class);
        appBrowserIntent.putExtra("url", newsArticles.get(position).getUrl());
        startActivity(appBrowserIntent);
    }
}