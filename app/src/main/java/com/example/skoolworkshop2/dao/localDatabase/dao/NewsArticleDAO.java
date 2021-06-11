package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.domain.NewsArticle;

import java.util.List;

@Dao
public interface NewsArticleDAO {

    @Insert()
    public void insertArticles(List<NewsArticle> newsArticles);

    @Query("SELECT * FROM NewsArticle ORDER BY dateTime(date) DESC")
    public List<NewsArticle> getAllNewsArticlesOrderedByDate();

    @Query("DELETE FROM NewsArticle")
    public void deleteNewsArticleTable();
}
