package com.example.skoolworkshop2.ui.User;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

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

        View root = findViewById(R.id.activity_my_account);
        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(4).setChecked(true);

        Button logOutButton = findViewById(R.id.activity_my_account_btn_log_out);
        logOutButton.setText("Log uit");

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

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

                    }
                }).start();
            }
        });
    }
}
