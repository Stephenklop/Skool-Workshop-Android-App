package com.example.skoolworkshop2.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private OnNoteListener mOnNoteListener;

    public NewsArticleAdapter(List<NewsArticle> newsArticles, Context context, OnNoteListener onNoteListener) {
        this.newsArticles = newsArticles;
        this.context = context;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_post, parent, false);
        MyViewHolder holder = new MyViewHolder(view, mOnNoteListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.title.setText(newsArticles.get(position).getName());
        Glide.with(this.context).load(newsArticles.get(position).getImgUrl()).into(holder.image);

    }

    public void updateList(List<NewsArticle> newsArticles){
        this.newsArticles = newsArticles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View url;
        ImageView image;
        TextView title;

        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            image = itemView.findViewById(R.id.item_blog_post_img);

            image.setClipToOutline(true);

            title = itemView.findViewById(R.id.item_blog_post_tv_title);
            url = itemView.findViewById(R.id.activity_home_rv_news_feed);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}



