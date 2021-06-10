package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.ProductDAO;
import com.example.skoolworkshop2.dao.UserDAO;

public class APIDAOFactory implements DAOFactory {
    @Override
    public ProductDAO getProductDAO() {
        return new APIProductDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new APIUserDAO();
    }

    @Override
    public NewsArticleDAO getNewsArticleDAO() {
        return new APINewsArticleDAO();
    }
}