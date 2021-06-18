package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIOrderDAO;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Reservation;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;

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
        APIOrderDAO dao = new APIOrderDAO();
        UserManager iem = new UserManager(getApplication());
        mOrderList = new ArrayList<>();
        Thread loadData = new Thread(() -> {
            mOrderList.addAll(dao.getAllReservationsFromUser(70));
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


}
