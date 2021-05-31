package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.WorkshopActivity;
import com.example.skoolworkshop2.domain.CultureDay;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CulturedayActivity extends FragmentActivity implements View.OnClickListener {
    LinearLayout mDetailTabsLl;
    View mTabsSelector;
    TextView mTabsOverviewTv;
    TextView mTabsContentTv;
    TextView mTabsInfoTv;
    TextView mTabsCostTv;
    Button mPriceBn;
    Button mParticipantsBn;
    Button mWorkshopsBn;
    Button mRoundsBn;

    private ImageButton mBackButton;
    private CultureDay cultureDay;
    private TextView mTitleTV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_details);

//        View root = (View) findViewById(R.id.activity_cultureday_details);
//        MenuController mc = new MenuController(root);
//        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
//        menu.getMenu().getItem(1).setChecked(true);




        mTitleTV = findViewById(R.id.activity_cultureday_details_tv_title);
        mBackButton = findViewById(R.id.activity_cultureday_details_btn_back);
        mPriceBn = findViewById(R.id.activity_cultureday_details_btn_price);
        mParticipantsBn = findViewById(R.id.activity_cultureday_details_btn_participant);
        mWorkshopsBn = findViewById(R.id.activity_cultureday_details_btn_workshop);
        mRoundsBn = findViewById(R.id.activity_cultureday_details_btn_round);



        cultureDay = (CultureDay) getIntent().getSerializableExtra("Cultureday");

        mTitleTV.setText(cultureDay.getName());
        mPriceBn.setText(cultureDay.getPrice() + ",-");
        mParticipantsBn.setText(cultureDay.getMaxParticipants() + " Deelnemers");
        mWorkshopsBn.setText(cultureDay.getWorkshops().size() + " Workshops");
        mRoundsBn.setText(cultureDay.getRounds() + " Rounds");

        mDetailTabsLl = findViewById(R.id.activity_cultureday_details_ll_tabs);
        mTabsSelector = mDetailTabsLl.findViewById(R.id.component_tabs_selector);

        mTabsOverviewTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_overview);
        mTabsContentTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_content);
        mTabsInfoTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_info);
        mTabsCostTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_costs);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_cultureday_details_fragment_txt, new CulturedayOverviewFragment(cultureDay))
                .commit();

        mTabsOverviewTv.setOnClickListener(this);
        mTabsContentTv.setOnClickListener(this);
        mTabsCostTv.setOnClickListener(this);
        mTabsInfoTv.setOnClickListener(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });
//        View root = findViewById(R.id.activity_cultureday_details_menu_icons);
//        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
//        menu.getMenu().getItem(2).setChecked(true);
//
//        MenuController mc = new MenuController(root);

    }

    @Override
    public void onClick(View v) {
        if (v == mTabsOverviewTv) {
            mTabsSelector.animate().x(mTabsOverviewTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_cultureday_details_fragment_txt, new CulturedayOverviewFragment(cultureDay))
                    .commit();
        } else if (v == mTabsContentTv) {
            mTabsSelector.animate().x(mTabsContentTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_cultureday_details_fragment_txt, new CulturedayContentFragment(cultureDay))
                    .commit();
        } else if (v == mTabsInfoTv){
            mTabsSelector.animate().x(mTabsInfoTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_cultureday_details_fragment_txt, new CulturedayInfoFragment(cultureDay))
                    .commit();
        } else if (v == mTabsCostTv){
            mTabsSelector.animate().x(mTabsCostTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_cultureday_details_fragment_txt, new CulturedayCostFragment(cultureDay))
                    .commit();
        }
    }
}
