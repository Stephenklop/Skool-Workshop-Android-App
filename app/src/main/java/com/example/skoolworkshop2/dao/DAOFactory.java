package com.example.skoolworkshop2.dao;

public interface DAOFactory {
    ProductDAO getProductDAO();
    UserDAO getUserDAO();
    NewsArticleDAO getNewsArticleDAO();
}
