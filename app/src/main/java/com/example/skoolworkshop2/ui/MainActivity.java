package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.CultureDay;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Workshop> workshopArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View root = (View) findViewById(R.id.activity_home);


        MenuController mc = new MenuController(root);
        this.workshopArrayList = new ArrayList<>();
        String[] desc = {"blabla", "test", "info", "price"};
        workshopArrayList.add(new Workshop(1, "Test", desc,55.55, "Test", 60, Category.DS));
        workshopArrayList.add(new Workshop(2, "Test", desc,55.55, "Test", 60, Category.BK));
        workshopArrayList.add(new Workshop(3, "Test", desc,55.55, "Test", 60, Category.MA));

        View searchPage = findViewById(R.id.activity_home_item_reservation);
        ImageView searchPageImg = searchPage.findViewById(R.id.item_dashboard_img_icon);
        TextView searchPageTv = searchPage.findViewById(R.id.item_dashboard_tv_txt);

        searchPageImg.setImageDrawable(getDrawable(R.drawable.ic_search));
        searchPageTv.setText("Workshops zoeken");
        searchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.sendToSearch();
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
                intent.putExtra("Cultureday", new CultureDay(1, "Cultureday", new String[]{"String", "Description", "Info", "Price"}, workshopArrayList, 4, 1650,"5/28/2021", 100));
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