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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static String adminToken;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adminToken = "pK4TdR13EQfl7l5a017Jzng3QUS67qYLmiR0OvBB/szH12AZI2WQezzJS8Xlm1Z6JSrkBJJMII1F6MxV2dKP14KmL7F8y2ZDIWGlif1/wSMaR3Q9ADFG7Mv1ljXa9L/YZQH0nwVVOtQtW9FpgKLvPVHC0QCuaAH8AZQ5zvsWEBYL+9yw4HPdNA9wrI7HC1X/";

        View root = (View) findViewById(R.id.activity_home);

        InfoEntityManager iem = new InfoEntityManager(this.getApplication());

        View points = findViewById(R.id.activity_home_item_points);
        TextView pointsTv = points.findViewById(R.id.item_points_tv_points);

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
                Intent intent = new Intent(getApplicationContext(), CulturedayActivity.class);
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


    }

    @Override
    public void onNoteClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticles.get(position).getUrl()));
        startActivity(browserIntent);
    }
}