package com.example.skoolworkshop2.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

public class NotificationsActivity extends AppCompatActivity {

    private OldNotificationAdapter oldNotificationAdapter;
    private NotificationsAdapter notificationsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("activity started");
        setContentView(R.layout.activity_notifications);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        if(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNotifications().size() > 0){
            TextView tvError = findViewById(R.id.activity_notifications_tv_error);
            tvError.setVisibility(View.GONE);
        }

        loadNotifications();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.activity_notifications_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread refreshNotifications = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            DAOFactory apidaoFactory = new APIDAOFactory();
                            LocalDb.getDatabase(getApplication()).getNotificationDAO().insertListOfNotification(apidaoFactory.getNotificationDAO().getNotificationsForTopic("main"));
                            if(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo() != null){
                                LocalDb.getDatabase(getApplication()).getNotificationDAO().insertListOfNotification(apidaoFactory.getNotificationDAO().getNotificationsForUser(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getId()));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadNotifications();
                                refreshLayout.setRefreshing(false);
                            }
                        });

                    }
                });
                refreshNotifications.start();
            }
        });

        ImageButton backButton = findViewById(R.id.activity_notifications_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void loadNotifications(){
        RecyclerView rv = findViewById(R.id.activity_notifications_rv_notifications);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.notificationsAdapter = new NotificationsAdapter(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNewNotifications(), getApplication(), new NotificationsAdapter.ClickListener() {
            @Override
            public void onClick() {
                //TODO ophalen notifications
                loadNotifications();
            }
        });
        rv.setAdapter(notificationsAdapter);
        rv.setLayoutManager(linearLayoutManager);

        RecyclerView oldRv = findViewById(R.id.activity_old_notifications_rv_notifications);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        this.oldNotificationAdapter = new OldNotificationAdapter(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllOldNotifications(), getApplicationContext());
        oldRv.setAdapter(oldNotificationAdapter);
        oldRv.setLayoutManager(linearLayoutManager1);
    }
}
