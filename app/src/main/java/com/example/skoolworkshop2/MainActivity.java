package com.example.skoolworkshop2;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mDetailTabsLl;
    View mTabsSelector;
    TextView mTabsOverviewTv;
    TextView mTabsContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        mDetailTabsLl = findViewById(R.id.activity_workshop_details_ll_tabs);
        mTabsSelector = mDetailTabsLl.findViewById(R.id.fragment_tabs_selector);

        mTabsOverviewTv = mDetailTabsLl.findViewById(R.id.fragment_tabs_tv_overview);
        mTabsContentTv = mDetailTabsLl.findViewById(R.id.fragment_tabs_tv_content);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_workshop_details_fragment_txt, new WorkshopOverviewFragment())
                .commit();

        mTabsOverviewTv.setOnClickListener(this);
        mTabsContentTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mTabsOverviewTv) {
            mTabsSelector.animate().x(mTabsOverviewTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopOverviewFragment())
                    .commit();
        } else if (v == mTabsContentTv) {
            mTabsSelector.animate().x(mTabsContentTv.getX()).setDuration(100);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_workshop_details_fragment_txt, new WorkshopContentFragment())
                    .commit();
        }
    }
}