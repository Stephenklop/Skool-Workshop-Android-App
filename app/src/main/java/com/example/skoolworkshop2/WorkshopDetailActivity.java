package com.example.skoolworkshop2;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class WorkshopDetailActivity extends AppCompatActivity {
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

        mWorkshopTitle.setText(getIntent().getStringExtra("NAME"));
//        mWorkshopDescription.setText(getIntent().getStringExtra("DESCRIPTION"));






    }

}
