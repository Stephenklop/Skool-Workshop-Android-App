package com.example.skoolworkshop2.ui.cultureDay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class CulturedayActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout mDetailTabsLl;
    private View mTabsSelector;
    private TextView mTabsOverviewTv;
    private TextView mTabsContentTv;
    private TextView mTabsInfoTv;
    private TextView mTabsCostTv;
    private Button mPriceBn;
    private Button mParticipantsBn;
    private Button mWorkshopsBn;
    private Button mRoundsBn;

    private ImageButton mBackButton;
    private Product cultureDay;
    private ImageView mCultureDayBanner;
    private TextView mTitleTV;
    private LocalAppStorage localAppStorage;

    public CulturedayActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_details);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        View root = (View) findViewById(R.id.activity_cultureday_details);

        MenuController mc = new MenuController(root);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        localAppStorage = new LocalAppStorage(getBaseContext());
        cultureDay = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByType("Cultuurdag").get(0);

        BottomNavigationView menu = findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(2).setChecked(true);


        mCultureDayBanner = findViewById(R.id.activity_cultureday_details_img_banner);
        mTitleTV = findViewById(R.id.activity_cultureday_details_tv_title);

        mPriceBn = findViewById(R.id.activity_cultureday_details_btn_price);
        mParticipantsBn = findViewById(R.id.activity_cultureday_details_btn_participant);
        mWorkshopsBn = findViewById(R.id.activity_cultureday_details_btn_workshop);
        mRoundsBn = findViewById(R.id.activity_cultureday_details_btn_round);

        mPriceBn.setText("€1674,-");
        mParticipantsBn.setText("100 Deelnemers");
        mWorkshopsBn.setText("4 Workshops");
        mRoundsBn.setText("3 Rondes");

        Glide.with(getBaseContext()).load(cultureDay.getSourceImage()).into(mCultureDayBanner);
        mTitleTV.setText(cultureDay.getName());
        mPriceBn.setText("€1674,-");


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
