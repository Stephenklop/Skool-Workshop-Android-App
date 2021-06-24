package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIOrderDAO;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity{

    private final String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private RecyclerView mRecyclerView;
    private ReservationAdapter mReservationAdapter;
    private List<Reservation> mOrderList;
    private LinearLayout mLayout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.activity_reservations);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        enableLoadingIndicator();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.activity_reservations_refresh);
        swipeRefreshLayout.setColorSchemeColors(getColor(R.color.main_orange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APIOrderDAO dao = new APIOrderDAO();
                Thread loadData = new Thread(() -> {
                    mOrderList = new ArrayList<>();
                    mOrderList.addAll(dao.getAllReservationsFromUser(70));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mReservationAdapter.setList(mOrderList);
                            swipeRefreshLayout.setRefreshing(false);

                            if(mOrderList.size() ==0 ){
                                new RoundedDialog(ReservationActivity.this, "Er zijn geen reserveringen op je account", "Als je net een reservering hebt geplaatst refresh dan deze pagina.");
                            }
                        }
                    });
                });
                loadData.start();
            }
        });

        APIOrderDAO dao = new APIOrderDAO();
        mOrderList = new ArrayList<>();
        Thread loadData = new Thread(() -> {
            mOrderList.addAll(dao.getAllReservationsFromUser(70));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mReservationAdapter.setList(mOrderList);
                    disableLoadingIndicator();
                    if(mOrderList.size() ==0 ){
                        new RoundedDialog(ReservationActivity.this, "Er zijn geen reserveringen op je account", "Als je net een reservering hebt geplaatst refresh dan deze pagina.");
                    }
                }
            });
        });
        try {
            loadData.join();;
            loadData.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLayout = findViewById(R.id.activity_reservations_ll);
        mReservationAdapter = new ReservationAdapter(mOrderList, getBaseContext());
        mRecyclerView = findViewById(R.id.activity_reservations_rv_reservations);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mReservationAdapter);
        mBackButton = findViewById(R.id.activity_invoice_data_btn_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));
            }
        });
    }

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_reservations_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_reservations_img_loading_indicator);
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
        LinearLayout loadingAlert = findViewById(R.id.activity_reservations_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_reservations_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }


}
