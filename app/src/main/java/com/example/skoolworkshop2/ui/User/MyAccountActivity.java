package com.example.skoolworkshop2.ui.User;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyAccountActivity extends AppCompatActivity {



    private final String LOG_TAG = this.getClass().getSimpleName();
    private Context context;


//    public MyAccountActivity(View root) {
//        context = root.getContext();
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        // Successfully logged in
        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();

//        // First Row
//        mFirstTwoItems = findViewById(R.id.activity_my_account_first_two_items_ll);
//        // Account
//        mRelativeLayout = mFirstTwoItems.findViewById(R.id.activity_my_account_item_account_data);
//        mDashBoardLinearLayout = mRelativeLayout.findViewById(R.id.item_dashboard_ll);
//        // Image
//        mDashBoardImageView = mDashBoardLinearLayout.findViewById(R.id.item_dashboard_img_icon);
//        mDashBoardImageView.setImageResource(R.drawable.ic_user);
//        // Textview
//        mDashBoardTextView = mDashBoardLinearLayout.findViewById(R.id.item_dashboard_tv_txt);



        //Account
        RelativeLayout Account = findViewById(R.id.activity_my_account_item_account_data);
        TextView AccountText = Account.findViewById(R.id.item_dashboard_tv_txt);
        AccountText.setText("Account");
        ImageView AccountImage = Account.findViewById(R.id.item_dashboard_img_icon);
        AccountImage.setImageResource(R.drawable.ic_user);
//        View AccountView = findViewById(R.id.activity_my_account_item_account_data);

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Redirecting to account data page");
                Intent toAccountData = new Intent(context, AccountDataActivity.class);

//                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle();

                context.startActivity(toAccountData);

//                Intent intent = new Intent(getApplicationContext(), UpdateAccountActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
            }

        });




        //Factuur Gegevens
        RelativeLayout FactuurGegevens = findViewById(R.id.activity_my_account_item_invoice_data);
        TextView FactuurGegevensText = FactuurGegevens.findViewById(R.id.item_dashboard_tv_txt);
        FactuurGegevensText.setText("Factuur Gegevens");
        ImageView FactuurGegevensImage = FactuurGegevens.findViewById(R.id.item_dashboard_img_icon);
        FactuurGegevensImage.setImageResource(R.drawable.ic_file);
        View FactuurGegevensView = findViewById(R.id.activity_my_account_item_invoice_data);

        //Reserveringen
        RelativeLayout Reserveringen = findViewById(R.id.activity_my_account_item_reservations);
        TextView ReserveringenText = Reserveringen.findViewById(R.id.item_dashboard_tv_txt);
        ReserveringenText.setText("Reserveringen");
        ImageView ReserveringenImage = Reserveringen.findViewById(R.id.item_dashboard_img_icon);
        ReserveringenImage.setImageResource(R.drawable.ic_folder);
        View ReserveringenView = findViewById(R.id.activity_my_account_item_reservations);

        //Skoolpartner
        RelativeLayout Skoolpartner = findViewById(R.id.activity_my_account_item_skoolpartner);
        TextView SkoolpartnerText = Skoolpartner.findViewById(R.id.item_dashboard_tv_txt);
        SkoolpartnerText.setText("Skoolpartner");
        ImageView SkoolpartnerImage = Skoolpartner.findViewById(R.id.item_dashboard_img_icon);
        SkoolpartnerImage.setImageResource(R.drawable.ic_star);
        View SkoolpartnerView = findViewById(R.id.activity_my_account_item_skoolpartner);

    }
}
