package com.example.skoolworkshop2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;

public class PointsLayoutTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);



        for (int i = 14; i < 18; i++) {
            TableLayout mTbl = findViewById(R.id.activity_points_tbl_point_history);
            TableRow mRow = (TableRow) LayoutInflater.from(this)
                    .inflate(R.layout.component_row_points, mTbl, false);

            TextView mDate = mRow.findViewById(R.id.component_row_points_tv_date);
            TextView mAction = mRow.findViewById(R.id.component_row_points_tv_action);
            TextView mAmount = mRow.findViewById(R.id.component_row_points_tv_amount);

            mDate.setText(i + " mei 2021");
            mAction.setText("Registratie");
            mAmount.setText("100");

            mTbl.addView(mRow, new TableRow.LayoutParams());
        }
    }
}
