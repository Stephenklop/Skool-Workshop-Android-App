package com.example.skoolworkshop2.logic.menuController;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.WorkshopActivity;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.CultureDay;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.ui.CulturedayActivity;
import com.example.skoolworkshop2.ui.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " Redirecting to home page");
                Intent toHome = new Intent(context, MainActivity.class);
                context.startActivity(toHome);
            }
        });

        //Test data for cultureday

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
                menu.getMenu().getItem(2).setChecked(true);
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
}
