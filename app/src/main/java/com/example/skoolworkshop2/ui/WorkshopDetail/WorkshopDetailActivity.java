package com.example.skoolworkshop2.ui.WorkshopDetail;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.WorkshopActivity;
import com.example.skoolworkshop2.domain.Workshop;


public class WorkshopDetailActivity extends FragmentActivity implements View.OnClickListener {
    LinearLayout mDetailTabsLl;
    View mTabsSelector;
    TextView mTabsOverviewTv;
    TextView mTabsContentTv;
    TextView mTabsInfoTv;
    TextView mTabsCostTv;


    private ImageButton mBackButton;

    private TextView mTitleTV;

    private Workshop workshop;

    private Button mPriceBn;
    private Button mParticipantsBn;
    private Button mDurationBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        mTitleTV = findViewById(R.id.activity_workshop_details_tv_title);
        mBackButton = findViewById(R.id.activity_details_details_btn_back);

        workshop = (Workshop) getIntent().getSerializableExtra("Workshop");

        mTitleTV.setText(workshop.getName());

        mDetailTabsLl = findViewById(R.id.activity_workshop_details_ll_tabs);
        mTabsSelector = mDetailTabsLl.findViewById(R.id.component_tabs_selector);

        mTabsOverviewTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_overview);
        mTabsContentTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_content);
        mTabsInfoTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_info);
        mTabsCostTv = mDetailTabsLl.findViewById(R.id.component_tabs_tv_costs);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_workshop_details_fragment_txt, new WorkshopOverviewFragment(workshop))
                .commit();

        mTabsOverviewTv.setOnClickListener(this);
        mTabsContentTv.setOnClickListener(this);
        mTabsCostTv.setOnClickListener(this);
        mTabsInfoTv.setOnClickListener(this);


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), WorkshopActivity.class);
                startActivity(backIntent);
            }
        });


        mPriceBn = findViewById(R.id.activity_workshop_detail_button_price);
        mParticipantsBn = findViewById(R.id.activity_workshop_detail_button_participants);
        mDurationBn = findViewById(R.id.activity_workshop_detail_button_duration);

        mPriceBn.setText(workshop.getPrice() + ",-");
        mParticipantsBn.setText(workshop.getMaxParticipants() + " deelnemers");
//        mDurationBn.setText(workshop. + " minuten");
    }

    @Override
    public void onClick(View v) {
        if (v == mTabsOverviewTv) {
            mTabsSelector.animate().x(mTabsOverviewTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopOverviewFragment(workshop))
                    .commit();
        } else if (v == mTabsContentTv) {
            mTabsSelector.animate().x(mTabsContentTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopContentFragment(workshop))
                    .commit();
        } else if (v == mTabsInfoTv){
            mTabsSelector.animate().x(mTabsInfoTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopInfoFragment(workshop))
                    .commit();
        } else if (v == mTabsCostTv){
            mTabsSelector.animate().x(mTabsCostTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopCostFragment(workshop))
                    .commit();
        }
    }

}
