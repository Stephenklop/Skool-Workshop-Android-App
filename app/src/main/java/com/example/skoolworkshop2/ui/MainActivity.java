package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.net.Uri;
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
//import androidx.room.Room;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.InfoEntity;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.NewsArticle;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.managers.localDb.InfoEntityManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




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


//        btn_addOne = findViewById(R.id.btn_addOne);
//
//        btn_addOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent (MainActivity.this, AddEditOne.class);
//                startActivity(intent);
//            }
//        });


        newsArticles = new ArrayList<NewsArticle>();

        recyclerView = findViewById(R.id.activity_home_rv_news_feed);

        fillNewsArticles();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new NewsArticleAdapter(newsArticles, this, this);
        recyclerView.setAdapter(mAdapter);



//        RecyclerView NewsFeedRv = findViewById(R.id.activity_home_rv_news_feed);
//        NewsFeedRv.setAdapter(new NewsArticleAdapter());

//            new RecyclerView.Adapter() {
//                class BlogPostViewHolder extends RecyclerView.ViewHolder {
//                    public BlogPostViewHolder(@NonNull @NotNull View itemView) {
//                        super(itemView);
//                        ImageView blogPostImg = itemView.findViewById(R.id.item_blog_post_img);
//                        blogPostImg.setClipToOutline(true);
//                    }
//                }
//
//                @NonNull
//                @NotNull
//                @Override
//                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//                    return new BlogPostViewHolder(LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_blog_post, parent, false));
//                }
//
//                @Override
//                public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
//
//                }
//
//                @Override
//                public int getItemCount() {
//                    return 5;
//                }
//            }
//        );
//        NewsFeedRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fillNewsArticles(){
        newsArticles.add(new NewsArticle("https://skoolworkshop.nl/jongeren-activiteiten/", "https://cdn-bnege.nitrocdn.com/MVgfApSlnIZMEMtTrPfeVWWDRvGvEHus/assets/static/optimized/rev-23fdb00/wp-content/uploads/2020/09/Dans-1024x517.jpg", "Jongeren activiteiten"));
        newsArticles.add(new NewsArticle("https://www.google.nl", "https://cdn-bnege.nitrocdn.com/MVgfApSlnIZMEMtTrPfeVWWDRvGvEHus/assets/static/optimized/rev-23fdb00/wp-content/uploads/2020/09/Dans-1024x517.jpg", "Jongeren activiteiten2"));
    }

    @Override
    public void onNoteClick(int position) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticles.get(position).getUrl()));
        startActivity(browserIntent);
    }
}