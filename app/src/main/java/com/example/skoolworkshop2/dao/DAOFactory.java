package com.example.skoolworkshop2.dao;

public interface DAOFactory {
    ProductDAO getProductDAO();
    UserDAO getUserDAO();
    NewsArticleDAO getNewsArticleDAO();
    FireBaseTokenDAO getFireBaseTokenDAO();
    NotificationDAO getNotificationDAO();
    OrderDAO getOrderDAO();
    CouponDAO getCouponDAO();
    EmailDAO getEmailDAO();
}
