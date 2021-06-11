package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.domain.NewsArticle;

import org.json.JSONObject;

import java.util.List;

public interface NewsArticleDAO {
    List getAllArticles();
    NewsArticle parseArticle(JSONObject jsonObject);
}
