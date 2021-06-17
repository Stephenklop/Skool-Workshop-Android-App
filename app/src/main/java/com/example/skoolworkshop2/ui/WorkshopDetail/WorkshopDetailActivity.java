package com.example.skoolworkshop2.ui.WorkshopDetail;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.VideoTestActivity;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.ui.workshop.WorkshopActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.w3c.dom.Text;

import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;

public class WorkshopDetailActivity extends FragmentActivity implements View.OnClickListener {
    LinearLayout mDetailTabsLl;
    View mTabsSelector;
    TextView mTabsOverviewTv;
    TextView mTabsContentTv;
    TextView mTabsInfoTv;
    TextView mTabsCostTv;


    private ImageButton mBackButton;
    private ImageView mWorkshopBanner;
    private TextView mTitleTV;

    private Product workshop;

    private Button mPriceBn;
    private Button mParticipantsBn;
    private Button mDurationBn;
    private Button mSendBn;

    private VideoView videoView;
    private boolean readyToPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        mBackButton = findViewById(R.id.activity_details_details_btn_back);
        mWorkshopBanner = findViewById(R.id.activity_workshop_details_img_banner);
        mTitleTV = findViewById(R.id.activity_workshop_details_tv_title);

        workshop = (Product) getIntent().getSerializableExtra("workshop");

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

        Glide.with(getBaseContext()).load(workshop.getSourceImage()).centerCrop().into(mWorkshopBanner);

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

        mPriceBn.setText("€150,-");
        mParticipantsBn.setText("25 deelnemers");
        mDurationBn.setText("60 min");

        initializePlayer();


        mWorkshopBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });

        CollapsingToolbarLayout appBarLayout = findViewById(R.id.activity_workshop_details_collapsingToolbar);
        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView != null){
                    if(videoView.isPlaying()){
                        videoView.pause();
                    } else if(videoView.getVisibility() == View.VISIBLE){
                        videoView.start();
                    } else {
                        playVideo();
                    }
                } else {
                    Toast t = new Toast(getApplicationContext());
                    t.setText("Geen video beschikbaar");
                    t.setDuration(Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

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


    private void initializePlayer() {

        VimeoExtractor.getInstance().fetchVideoWithURL(workshop.getVideo(), null, new OnVimeoExtractionListener() {
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
                videoView = findViewById(R.id.activity_workshop_details_vv_banner);

                videoView.setBackgroundColor(Color.TRANSPARENT);
                Uri video = Uri.parse(stream);
                videoView.setVideoURI(video);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        readyToPlay = true;
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.setVisibility(View.GONE);
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



}
