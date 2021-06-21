package com.example.skoolworkshop2.ui.cultureDay;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;


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

    private VideoView videoView;

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

        initializePlayer();

        AppBarLayout appBarLayout = findViewById(R.id.activity_cultureday_details_appBar);
        ImageView playIcon = findViewById(R.id.activity_cultureday_detail_play);
        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView != null){
                    if(videoView.isPlaying()){
                        videoView.pause();
                        playIcon.setVisibility(View.VISIBLE);
                    } else if(videoView.getVisibility() == View.VISIBLE){
                        videoView.start();
                        playIcon.setVisibility(View.GONE);
                    } else {
                        playVideo();
                        playIcon.setVisibility(View.GONE);
                    }
                }
            }
        });


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

    private void initializePlayer() {
        System.out.println(getId(cultureDay.getVideo()));
        VimeoExtractor.getInstance().fetchVideoWithIdentifier(getId(cultureDay.getVideo()), null, new OnVimeoExtractionListener() {
            @Override
            public void onSuccess(VimeoVideo video) {
                String hdStream = video.getStreams().get("720p");
                System.out.println("VIMEO VIDEO STREAM" + hdStream);
                if (hdStream != null) {
                    prepareVideo(hdStream);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void prepareVideo(final String stream) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoView = findViewById(R.id.activity_cultureday_details_vv_banner);

                videoView.setBackgroundColor(Color.TRANSPARENT);
                Uri video = Uri.parse(stream);
                videoView.setVideoURI(video);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

    }

    private void playVideo(){
        videoView.setVisibility(View.VISIBLE);
        videoView.requestFocus();
        videoView.start();
    }

    private String getId(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;
        return string.substring(i, j);
    }
}
