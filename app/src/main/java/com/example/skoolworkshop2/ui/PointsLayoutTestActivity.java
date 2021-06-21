package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.User.MyAccountActivity;

public class PointsLayoutTestActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        View points = findViewById(R.id.activity_points_item_points);
        TextView pointsTv = points.findViewById(R.id.item_points_tv_points);

        UserManager iem = new UserManager(getApplication());

        String pointsStrStart = "Je hebt ";
        String pointsStr = pointsStrStart + iem.getInfo().getPoints() + " punten";
        Spannable pointsSpannable = new SpannableString(pointsStr);
        pointsSpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)),
                pointsStrStart.length(),
                pointsStrStart.length() + String.valueOf(iem.getInfo().getPoints()).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pointsSpannable.setSpan(new RelativeSizeSpan(1.2f),
                pointsStrStart.length(),
                pointsStrStart.length() + String.valueOf(iem.getInfo().getPoints()).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pointsTv.setText(pointsSpannable, TextView.BufferType.SPANNABLE);

        TextView moneyPoints = points.findViewById(R.id.item_points_tv_value);

        String moneyStrStart = "Waarde ";
        String moneyStr = moneyStrStart + "€" + String.format("%.2f", (1.00 * iem.getInfo().getPoints() * 0.03)).replace(".", ",");
        Spannable moneySpannable = new SpannableString(moneyStr);
        moneySpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)),
                moneyStrStart.length(),
                moneyStrStart.length() + ("€" + String.format("%.2f", (1.00 * iem.getInfo().getPoints() * 0.03))).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        moneySpannable.setSpan(new RelativeSizeSpan(1.2f),
                moneyStrStart.length(),
                moneyStrStart.length() + ("€" + String.format("%.2f", (1.00 * iem.getInfo().getPoints() * 0.03))).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        moneyPoints.setText(moneySpannable, TextView.BufferType.SPANNABLE);



        ImageButton backButton = (ImageButton) findViewById(R.id.activity_points_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyAccountActivity.class).putExtra("USERNAME", iem.getInfo().getUsername()));
            }
        });



//        for (int i = 14; i < 18; i++) {
//            TableLayout mTbl = findViewById(R.id.activity_points_tbl_point_history);
//            TableRow mRow = (TableRow) LayoutInflater.from(this)
//                    .inflate(R.layout.component_row_points, mTbl, false);
//
//            TextView mDate = mRow.findViewById(R.id.component_row_points_tv_date);
//            TextView mAction = mRow.findViewById(R.id.component_row_points_tv_action);
//            TextView mAmount = mRow.findViewById(R.id.component_row_points_tv_amount);
//
//            mDate.setText(i + " mei 2021");
//            mAction.setText("Registratie");
//            mAmount.setText("100");
//
//            mTbl.addView(mRow, new TableRow.LayoutParams());
//        }
    }
}
