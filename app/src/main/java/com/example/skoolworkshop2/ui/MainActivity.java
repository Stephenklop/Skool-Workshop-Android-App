package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.room.Room;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private APIDAOFactory apidaoFactory;
    private LocalAppStorage localAppStorage;
    private MenuController menuController;
    private List<Product> workshops;
    private Product cultureDay;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //start the db
        Room.databaseBuilder(this, LocalDb.class, "LocalDb");

        //make manager for the entity
        InfoEntityManager iem = new InfoEntityManager(this.getApplication());

        //insert test information into db
        iem.updateInfo(new InfoEntity("Bas Buijsen", "bbuijsen@gmail.com", "token", "blabla"));

        Log.d("MAINACTIVITY", iem.getInfo().getPassword());


        View root = (View) findViewById(R.id.activity_home);

        localAppStorage = new LocalAppStorage(getBaseContext());
        menuController = new MenuController(root);
        apidaoFactory = new APIDAOFactory();

        System.out.println("SHOPPING CART: " + Paper.book().read("cartItems"));

        Thread loadProducts = new Thread(() -> {
            workshops = apidaoFactory.getProductDAO().getAllProductsByCategory(23);
            cultureDay = apidaoFactory.getProductDAO().getAllProductsByCategory(28).get(0);

            localAppStorage.createList("workshops", workshops);
            System.out.println(localAppStorage.getList("workshops"));

            localAppStorage.createList("cultureDay", cultureDay);
        });

        try {
            loadProducts.join();
            loadProducts.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        RecyclerView NewsFeedRv = findViewById(R.id.activity_home_rv_news_feed);
        NewsFeedRv.setAdapter(new RecyclerView.Adapter() {
            class BlogPostViewHolder extends RecyclerView.ViewHolder {
                public BlogPostViewHolder(@NonNull @NotNull View itemView) {
                    super(itemView);
                    ImageView blogPostImg = itemView.findViewById(R.id.item_blog_post_img);
                    blogPostImg.setClipToOutline(true);
                }
            }

            @NonNull
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                return new BlogPostViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog_post, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        NewsFeedRv.setLayoutManager(new LinearLayoutManager(this));
    }
}