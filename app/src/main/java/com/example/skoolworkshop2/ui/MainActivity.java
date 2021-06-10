package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        pointsTv.setText("Je hebt " + iem.getInfo().getPoints() + " punten");

        TextView moneyPoints = points.findViewById(R.id.item_points_tv_value);
//        moneyPoints.setText("Waarde €" + (1.00 * iem.getInfo().getPoints() * 0.03) + ",-");

        localAppStorage = new LocalAppStorage(getBaseContext());
        menuController = new MenuController(root);
        apidaoFactory = new APIDAOFactory();

//        System.out.println("SHOPPING CART: " + Paper.book().read("cartItems"));

//        Thread loadProducts = new Thread(() -> {
//            workshops = apidaoFactory.getProductDAO().getAllProductsByCategory(23);
//            cultureDay = apidaoFactory.getProductDAO().getAllProductsByCategory(28).get(0);
//
//            localAppStorage.createList("workshops", workshops);
//            System.out.println(localAppStorage.getList("workshops"));
//
//            localAppStorage.createList("cultureDay", cultureDay);
//        });
//
//        try {
//            loadProducts.join();
//            loadProducts.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        View searchPage = findViewById(R.id.activity_home_item_reservation);
        ImageView searchPageImg = searchPage.findViewById(R.id.item_dashboard_img_icon);
        TextView searchPageTv = searchPage.findViewById(R.id.item_dashboard_tv_txt);

        searchPageImg.setImageDrawable(getDrawable(R.drawable.ic_search));
        searchPageTv.setText("Workshops zoeken");
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
        cultureDayTv.setText("Cultuurdag bekijken");
        cultureDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CulturedayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        // example newsfeed implementation


//        btn_addOne = findViewById(R.id.btn_addOne);
//
//        btn_addOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent (MainActivity.this, AddEditOne.class);
//                startActivity(intent);
//            }
//        });


//        Thread APIThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                APIDAOFactory apidaoFactory= new APIDAOFactory();
//                NewsArticleDAO newsArticleDAO = apidaoFactory.getNewsArticleDAO();
//                newsArticles = newsArticleDAO.getAllArticles();
//                System.out.println(newsArticles);
//            }
//        });


        Thread APIThread = new Thread(() -> {
            APIDAOFactory apidaoFactory = new APIDAOFactory();
            NewsArticleDAO newsArticleDAO = apidaoFactory.getNewsArticleDAO();
            newsArticles = newsArticleDAO.getAllArticles();
            System.out.println("LOADED ARTICLES: " + newsArticles);
        });
        Thread recyclerViewThread = new Thread(() -> {
            // Calling the method to build the recyclerview
            recyclerView = findViewById(R.id.activity_home_rv_news_feed);
        });
        Thread adapterThread = new Thread(() -> {
            mAdapter = new NewsArticleAdapter(newsArticles, MainActivity.this, MainActivity.this);
            recyclerView.setAdapter(mAdapter);
        });
        // Start and join the threads.
        try {
            APIThread.start();
            APIThread.join();
            recyclerViewThread.start();
            recyclerViewThread.join();
            adapterThread.start();
            adapterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        RecyclerView NewsFeedRv = findViewById(R.id.activity_home_rv_news_feed);
//        NewsFeedRv.setAdapter(new NewsArticleAdapter());

//            new RecyclerView.Adapter() {
    }

    @Override
    public void onNoteClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticles.get(position).getUrl()));
        startActivity(browserIntent);
    }
}