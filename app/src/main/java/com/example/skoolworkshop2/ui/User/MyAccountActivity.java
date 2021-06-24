package com.example.skoolworkshop2.ui.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

public class MyAccountActivity extends AppCompatActivity {



    private final String LOG_TAG = this.getClass().getSimpleName();
    private Context context;


    private LinearLayout mLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        final boolean[] buttonsEnabled = {true};


        View root = findViewById(R.id.activity_my_account);
        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(4).setChecked(true);

        Button logOutButton = findViewById(R.id.activity_my_account_btn_log_out);
        logOutButton.setText("Log uit");

        SwipeRefreshLayout refreshLayout = findViewById(R.id.activity_account_refresh);
        refreshLayout.setColorSchemeColors(getColor(R.color.main_orange));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DAOFactory apidaoFactory = new APIDAOFactory();
                Thread updateUserInfo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("updating account info");
                        UserDAO userDAO = apidaoFactory.getUserDAO();
                        int id = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getId();
                        LocalDb.getDatabase(getApplication()).getCustomerDAO().deleteCustomer();
                        System.out.println("deleted customer");
                        Customer insertCustomer = userDAO.getCustomerInfo(id);
                        System.out.println(insertCustomer.toString());
                        LocalDb.getDatabase(getApplication()).getCustomerDAO().addCustomer(insertCustomer);
                        System.out.println("added customer with updated info");
                        LocalDb.getDatabase(getApplication()).getUserDAO().deleteInfo();
                        System.out.println("deleted user");
                        User user = userDAO.getLastUser();
                        System.out.println(user.toString());
                        LocalDb.getDatabase(getApplication()).getUserDAO().insertInfo(user);
                        System.out.println("added user with updated info");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
                updateUserInfo.start();
            }
        });

        mLoader = findViewById(R.id.activity_login_ll_loading_alert);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutButton.setEnabled(false);
                buttonsEnabled[0] = false;
                enableLoadingIndicator();
                deleteUser();
            }
        });


        // first icon
        View icon1 = findViewById(R.id.activity_my_account_item_account_data);
        TextView textIcon1 = icon1.findViewById(R.id.item_dashboard_tv_txt);
        textIcon1.setText("Account");
        ImageView imageIcon1 = icon1.findViewById(R.id.item_dashboard_img_icon);
        imageIcon1.setImageResource(R.drawable.ic_user);
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountDataActivity.class));
            }
        });


        //second icon
        View icon2 = findViewById(R.id.activity_my_account_item_invoice_data);
        TextView textIcon2 = icon2.findViewById(R.id.item_dashboard_tv_txt);
        textIcon2.setText("Factuur Gegevens");
        ImageView imageIcon2 = icon2.findViewById(R.id.item_dashboard_img_icon);
        imageIcon2.setImageResource(R.drawable.ic_file);
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InvoiceAdressActivity.class));
            }
        });


        //third icon
        View icon3 = findViewById(R.id.activity_my_account_item_reservations);
        TextView textIcon3 = icon3.findViewById(R.id.item_dashboard_tv_txt);
        textIcon3.setText("Boekingen");
        ImageView imageIcon3 = icon3.findViewById(R.id.item_dashboard_img_icon);
        imageIcon3.setImageResource(R.drawable.ic_folder);
        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonsEnabled[0]){
                    startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                }
            }
        });

        //fourth icon
        View icon4 = findViewById(R.id.activity_my_account_item_skoolpartner);
        TextView textIcon4 = icon4.findViewById(R.id.item_dashboard_tv_txt);
        textIcon4.setText("Skoolpartner");
        ImageView imageIcon4 = icon4.findViewById(R.id.item_dashboard_img_icon);
        imageIcon4.setImageResource(R.drawable.ic_star);
        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonsEnabled[0]){
                    startActivity(new Intent(getApplicationContext(), PointsActivity.class));
                }
            }
        });

    }

    private void deleteUser(){
        DAOFactory apidaoFactory = new APIDAOFactory();
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                token[0] = task.getResult();
                System.out.println(task.getResult());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        apidaoFactory.getFireBaseTokenDAO().deleteToken(task.getResult(), LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getId());
                        System.out.println("added token");
                        LocalDb.getDatabase(getApplication()).getUserDAO().deleteInfo();
                        System.out.println("deleted user");
                        LocalDb.getDatabase(getApplication()).getCustomerDAO().deleteCustomer();
                        System.out.println("deleted billing address");
                        LocalDb.getDatabase(getApplication()).getUserDAO().deleteAdress();
                        System.out.println("deleted shipping address");
                        LocalDb.getDatabase(getApplication()).getUserDAO().deleteShippingAddress();
                        System.out.println("deleted notifications");
                        LocalDb.getDatabase(getApplication()).getNotificationDAO().deletePersonalNotifications();
                        System.out.println("deleted customer");
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                    }
                }).start();
            }
        });
    }

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backgroundBlur = findViewById(R.id.activity_account_loading_background);
        backgroundBlur.setVisibility(View.VISIBLE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        loadingAlert.setAlpha(0);
        loadingAlert.setVisibility(View.VISIBLE);
        loadingAlert.animate().alpha(1).setDuration(200).start();
        avd.start();
    }

    private void disableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backgroundBlur = findViewById(R.id.activity_account_loading_background);
        backgroundBlur.setVisibility(View.GONE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}
