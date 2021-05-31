package com.example.skoolworkshop2.dao.websiteData;

import com.example.skoolworkshop2.dao.websiteData.Api.ApiAccountDao;
import com.example.skoolworkshop2.dao.websiteData.Api.ApiOrder;
import com.example.skoolworkshop2.dao.websiteData.Api.ApiProductDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.AccountDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.OrderDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.ProductDao;

public class ApiWebsiteInfoFactory implements WebsiteInfoFactory {
    @Override
    public ProductDao getProductDao() {
        return new ApiProductDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new ApiOrder();
    }

    @Override
    public AccountDao getAccountDao() {
        return new ApiAccountDao();
    }
}
