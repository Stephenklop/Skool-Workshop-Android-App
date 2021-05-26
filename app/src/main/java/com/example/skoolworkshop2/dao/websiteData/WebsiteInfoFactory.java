package com.example.skoolworkshop2.dao.websiteData;

import com.example.skoolworkshop2.dao.websiteData.interfaces.Account;
import com.example.skoolworkshop2.dao.websiteData.interfaces.Order;
import com.example.skoolworkshop2.dao.websiteData.interfaces.Product;

public interface WebsiteInfoFactory {
    Product getProductDao();
    Order getOrderDao();
    Account getAccountDao();
}
