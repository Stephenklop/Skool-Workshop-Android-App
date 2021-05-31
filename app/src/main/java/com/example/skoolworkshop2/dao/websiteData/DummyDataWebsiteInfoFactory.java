package com.example.skoolworkshop2.dao.websiteData;

import com.example.skoolworkshop2.dao.websiteData.DummyData.DummyDataAccountDao;
import com.example.skoolworkshop2.dao.websiteData.DummyData.DummyDataOrderDao;
import com.example.skoolworkshop2.dao.websiteData.DummyData.DummyDataProductDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.AccountDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.OrderDao;
import com.example.skoolworkshop2.dao.websiteData.interfaces.ProductDao;

public class DummyDataWebsiteInfoFactory implements WebsiteInfoFactory{
    @Override
    public ProductDao getProductDao() {
        return new DummyDataProductDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new DummyDataOrderDao();
    }

    @Override
    public AccountDao getAccountDao() {
        return new DummyDataAccountDao();
    }
}
