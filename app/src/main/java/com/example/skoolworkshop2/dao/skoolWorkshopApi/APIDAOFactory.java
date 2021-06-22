package com.example.skoolworkshop2.dao.skoolWorkshopApi;

import com.example.skoolworkshop2.dao.CouponDAO;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.FireBaseTokenDAO;
import com.example.skoolworkshop2.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.NotificationDAO;
import com.example.skoolworkshop2.dao.OrderDAO;
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

    @Override
    public FireBaseTokenDAO getFireBaseTokenDAO() {
        return new APIFireBaseTokenDAO();
    }

    @Override
    public NotificationDAO getNotificationDAO() {
        return new APINotificationDAO();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new APIOrderDAO();
    }

    @Override
    public CouponDAO getCouponDAO() {
        return new APICouponDAO();
    }
}