package com.example.skoolworkshop2.dao.websiteData;

import com.example.skoolworkshop2.dao.websiteData.interfaces.AccountDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.OrderDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.ProductDao;

public interface WebsiteInfoFactory {
    ProductDao getProductDao();
    OrderDao getOrderDao();
    AccountDao getAccountDao();
}
