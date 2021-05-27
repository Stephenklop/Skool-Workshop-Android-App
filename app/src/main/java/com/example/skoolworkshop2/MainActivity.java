package com.example.skoolworkshop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    LinearLayout tabsll = findViewById(R.id.tabs);
//    TextView tv1 = tabsll.findViewById(R.id.a);
//    TextView tv2= tabsll.findViewById(R.id.b);
//    TextView tv3 = tabsll.findViewById(R.id.c);
//    TextView tv4 = tabsll.findViewById(R.id.d);
    private final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = new Intent(this, WorkshopActivity.class);
        Log.d(LOG_TAG, "onCreate: skipped to workshopactivity");
        startActivity(intent);
//        tv1.setSelected(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b:
//            tv2.setSelected(true);
        }
    }
}