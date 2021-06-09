package com.example.skoolworkshop2.ui.User;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;

public class MyAccountActivity extends AppCompatActivity {

    // Account
    private LinearLayout mFirstTwoItems;
    private RelativeLayout mRelativeLayout;
    private LinearLayout mDashBoardLinearLayout;
    private ImageView mDashBoardImageView;
    private TextView mDashBoardTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        // Successfully logged in
        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();
        // First Row
        mFirstTwoItems = findViewById(R.id.activity_my_account_first_two_items_ll);
        // Account
        mRelativeLayout = mFirstTwoItems.findViewById(R.id.activity_my_account_item_account_data);
        mDashBoardLinearLayout = mRelativeLayout.findViewById(R.id.item_dashboard_ll);
        // Image
        mDashBoardImageView = mDashBoardLinearLayout.findViewById(R.id.item_dashboard_img_icon);
        mDashBoardImageView.setImageResource(R.drawable.ic_user);
        // Textview
        mDashBoardTextView = mDashBoardLinearLayout.findViewById(R.id.item_dashboard_tv_txt);
    }
}
