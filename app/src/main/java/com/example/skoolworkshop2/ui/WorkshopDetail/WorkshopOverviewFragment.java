package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.ui.CulturedayBookingActivity;
import com.example.skoolworkshop2.ui.CulturedayQuestionActivity;

public class WorkshopOverviewFragment extends Fragment {
    private TextView mDescriptionTv;
    private TextView mPriceTv;
    private TextView mParticipants;
    private Workshop workshop;

    private Button mBookingBn;
    private Button mInfoBn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_overview, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_overview_tv_description);
        mPriceTv = root.findViewById(R.id.fragment_workshop_overview_tv_price);
        mParticipants = root.findViewById(R.id.fragment_worksjop_overview_tv_details);

        mBookingBn = root.findViewById(R.id.fragment_workshop_overview_btn_booking);
        mInfoBn = root.findViewById(R.id.fragment_workshop_overview_btn_info);

        mParticipants.setText("60 minuten | Maximaal " + workshop.getMaxParticipants() + " deelnemers");
        mPriceTv.setText(workshop.getPrice() + ",-");
        mDescriptionTv.setText(workshop.getDescription()[0]);

        mBookingBn.setText("Boek Direct Online");
        mInfoBn.setText("Vraag Meer Informatie Aan");

        mBookingBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkshopBookingActivity.class);
                startActivity(intent);
            }
        });
        mInfoBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkshopQuestionActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    public WorkshopOverviewFragment(Workshop workshop){
        this.workshop = workshop;
    }

}