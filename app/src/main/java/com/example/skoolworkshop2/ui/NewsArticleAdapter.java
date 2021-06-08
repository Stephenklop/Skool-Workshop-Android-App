package com.example.skoolworkshop2.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.NewsArticle;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.MyViewHolder> {
    List<NewsArticle> newsArticles;
    Context context;

    public NewsArticleAdapter(List<NewsArticle> newsArticles, Context context) {
        this.newsArticles = newsArticles;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_post, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.title.setText(newsArticles.get(position).getName());
        Glide.with(this.context).load(newsArticles.get(position).getImgUrl()).into(holder.image);
        //TODO: Link to website
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_blog_post_img);
            title = itemView.findViewById(R.id.item_blog_post_tv_title);
        }
    }
}



