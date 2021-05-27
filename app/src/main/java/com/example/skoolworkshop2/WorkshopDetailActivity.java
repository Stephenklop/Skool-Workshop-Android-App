package com.example.skoolworkshop2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


public class WorkshopDetailActivity extends FragmentActivity {
    private ImageView mCoverView;
    private TextView mWorkshopTitle;
    private TextView mWorkshopDescription;
    private TextView mWorkshopPrice;
    private View mFragmentContainerView;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;


    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);

        LinearLayout tabsLayout = findViewById(R.id.activity_workshop_details_ll_tabs);
        View overviewButton = tabsLayout.findViewById(R.id.fragment_tabs_tv_overview);

        overviewButton.setOnClickListener(v -> {
            System.out.println("Overview clicked!");

            // Create an instance of the WorkshopOverviewFragment.
            WorkshopOverviewFragment workshopOverviewFragment = new WorkshopOverviewFragment();

            // Add the fragment to the fragment container.
            getSupportFragmentManager().beginTransaction().add(R.id.activity_workshop_details_fragment_txt, workshopOverviewFragment).commit();
        });

        //Set ID

//
//        TextView tv1 = tabsll.findViewById(R.id.a);
//        TextView tv2= tabsll.findViewById(R.id.b);
//        TextView tv3 = tabsll.findViewById(R.id.c);
//        TextView tv4 = tabsll.findViewById(R.id.d);


        mFragmentContainerView = (View) findViewById(R.id.activity_workshop_details_fragment_txt);
        mCoverView = (ImageView) findViewById(R.id.activity_workshops_details_cover);
        mWorkshopTitle = (TextView) findViewById(R.id.activity_workshop_title);
//        mWorkshopDescription = (TextView) findViewById(R.id.activity_workshops_details_description);


//        mCoverView = (ImageView) findViewById(R.id.activity_workshops_details_cover);
//        mWorkshopTitle = (TextView) findViewById(R.id.activity_workshops_details_title);
//        mWorkshopDescription = (TextView) findViewById(R.id.activity_workshops_details_description);







    }

}
