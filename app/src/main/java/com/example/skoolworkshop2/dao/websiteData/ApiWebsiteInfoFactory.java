package com.example.skoolworkshop2.dao.websiteData;

import com.example.skoolworkshop2.dao.websiteData.Api.ApiAccount;
import com.example.skoolworkshop2.dao.websiteData.Api.ApiOrder;
import com.example.skoolworkshop2.dao.websiteData.Api.ApiProduct;
import com.example.skoolworkshop2.dao.websiteData.interfaces.Account;
import com.example.skoolworkshop2.dao.websiteData.interfaces.Order;
import com.example.skoolworkshop2.dao.websiteData.interfaces.Product;

public class ApiWebsiteInfoFactory implements WebsiteInfoFactory {
    @Override
    public Product getProductDao() {
        return new ApiProduct();
    }

    @Override
    public Order getOrderDao() {
        return new ApiOrder();
    }

    @Override
    public Account getAccountDao() {
        return new ApiAccount();
    }
}
