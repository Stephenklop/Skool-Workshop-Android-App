package com.example.skoolworkshop2.dao.localDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.skoolworkshop2.dao.OrderDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.BankDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.CustomerDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.NotificationDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.NewsArticleDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.ProductDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.QuizDAO;
import com.example.skoolworkshop2.dao.localDatabase.dao.ShoppingCartDAO;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.NewsArticle;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Quiz;
import com.example.skoolworkshop2.domain.Reservation;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.domain.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Customer.class, Product.class, Quiz.class, Bank.class, ShoppingCartItem.class, NewsArticle.class, BillingAddress.class, ShippingAddress.class, Order.class, Notification.class, Reservation.class}, version = 1)

public abstract class LocalDb extends RoomDatabase {

    private static volatile LocalDb INSTANCE;
    public static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(4);

    abstract public UserDAO getUserDAO();
    abstract public ShoppingCartDAO getShoppingCartDAO();
    abstract public ProductDAO getProductDAO();
    abstract public BankDAO getBankDAO();
    abstract public QuizDAO getQuizDAO();
    abstract public CustomerDAO getCustomerDAO();
    abstract public NewsArticleDAO getNewsArticleDAO();
    abstract public NotificationDAO getNotificationDAO();
    abstract public OrderDAO getOrderDAO();

    public static LocalDb getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (LocalDb.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDb.class, "localDatabase").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}