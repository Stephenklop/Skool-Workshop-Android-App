package com.example.skoolworkshop2.logic.menuController;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.ui.shoppingCart.ShoppingCartActivity;
import com.example.skoolworkshop2.ui.workshop.WorkshopActivity;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;
import com.example.skoolworkshop2.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MenuController {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private BottomNavigationView menu;
    private BottomNavigationItemView home;
    private BottomNavigationItemView search;
    private BottomNavigationItemView shoppingCart;
    private BottomNavigationItemView account;
    private BottomNavigationItemView sun;

    private List<Workshop> workshopArrayList;


    private Context context;


    public MenuController(View root){
        menu = root.findViewById(R.id.activity_menu_buttons);
        home = menu.findViewById(R.id.menu_bottom_icons_menu_home);
        sun = menu.findViewById(R.id.menu_bottom_icons_menu_sun);
        search = menu.findViewById(R.id.menu_bottom_icons_menu_search);
        shoppingCart = menu.findViewById(R.id.menu_bottom_icons_menu_shopping_cart);
        account = menu.findViewById(R.id.menu_bottom_icons_menu_account);
        context = root.getContext();

        View statusBar = root.getRootView().findViewById(android.R.id.statusBarBackground);
        View navigationBar = root.getRootView().findViewById(android.R.id.navigationBarBackground);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to home page");
                Intent toHome = new Intent(context, MainActivity.class);

                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context).toBundle();

                context.startActivity(toHome);
            }
        });

        //Test data for cultureday

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to search page");
                Intent toSearch = new Intent(context, WorkshopActivity.class);

                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context).toBundle();
                context.startActivity(toSearch);
            }
        });

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, " Redirecting to shoppingcart page");
                menu.getMenu().getItem(3).setChecked(true);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to account page");
                menu.getMenu().getItem(4).setChecked(true);
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to cultureday page");
                Intent toCultureDay = new Intent(context, CulturedayActivity.class);

                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context).toBundle();
                context.startActivity(toCultureDay);
            }
        });

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to shopping cart page");
                Intent toShoppingCart = new Intent(context, ShoppingCartActivity.class);
                context.startActivity(toShoppingCart);
            }
        });
    }

    public void sendToHome(){
        Intent toSearch = new Intent(context, MainActivity.class);
        context.startActivity(toSearch);
    }

    public void sendToSearch(){
        Intent toSearch = new Intent(context, WorkshopActivity.class);
        context.startActivity(toSearch);
    }

    public void sendToCultureDay(){
        Intent toCultureDay = new Intent(context, CulturedayActivity.class);
        context.startActivity(toCultureDay);
    }

    public void sendToShoppingCart() {
        Intent toShoppingCart = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(toShoppingCart);
    }
}