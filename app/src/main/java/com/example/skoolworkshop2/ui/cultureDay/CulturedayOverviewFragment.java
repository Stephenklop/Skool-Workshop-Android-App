package com.example.skoolworkshop2.ui.cultureDay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;

public class CulturedayOverviewFragment extends Fragment {
    private TextView mDescriptionTv;
    private TextView mPriceTv;
    private TextView mParticipants;
    private Product cultureDay;

    private Button mBookingBn;
    private Button mInfoBn;

    public CulturedayOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_overview, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_overview_tv_description);
        mPriceTv = root.findViewById(R.id.fragment_workshop_overview_tv_price);
        mParticipants = root.findViewById(R.id.fragment_worksjop_overview_tv_details);
        mBookingBn = root.findViewById(R.id.fragment_workshop_overview_btn_booking);
        mInfoBn = root.findViewById(R.id.fragment_workshop_overview_btn_info);

//        mParticipants.setText(cultureDay.getWorkshops().size() + " Workshops, " + cultureDay.getRounds() + " Rondes | Maximaal " + cultureDay.getMaxParticipants() + " Deelnemers");
        mPriceTv.setText("€1674,-");
        mDescriptionTv.setText(Html.fromHtml(cultureDay.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        mBookingBn.setText("Boek Direct Online");
        mInfoBn.setText("Vraag Meer Informatie Aan");

        mBookingBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CultureDayBookingActivity.class);
                intent.putExtra("cultureDay", cultureDay);
                startActivity(intent);
            }
        });
        mInfoBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CulturedayQuestionActivity.class);
                startActivity(intent);
            }
        });

        return root;


    }


    public CulturedayOverviewFragment(Product cultureDay){
        this.cultureDay = cultureDay;
    }

}
