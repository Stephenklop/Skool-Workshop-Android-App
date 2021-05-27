package com.example.skoolworkshop2.logic.menuController;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.WorkshopActivity;
import com.example.skoolworkshop2.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.io.Console;

public class MenuController {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private View menu;
    private BottomNavigationItemView home;
    private BottomNavigationItemView search;
    private BottomNavigationItemView shoppingCart;
    private BottomNavigationItemView account;

    private Context context;


    public MenuController(View root){
        menu = root.findViewById(R.id.activity_menu_buttons);
        home = menu.findViewById(R.id.menu_bottom_icons_menu_home);
        search = menu.findViewById(R.id.menu_bottom_icons_menu_sun);
        shoppingCart = menu.findViewById(R.id.menu_bottom_icons_menu_shopping_cart);
        account = menu.findViewById(R.id.menu_bottom_icons_menu_account);
        context = root.getContext();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to home page");
                Intent toHome = new Intent(context, MainActivity.class);
                context.startActivity(toHome);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to search page");
                Intent toSearch = new Intent(context, WorkshopActivity.class);
                context.startActivity(toSearch);
            }
        });

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to shoppingcart page");
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to account page");
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
}
