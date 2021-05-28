package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;

public class WorkshopOverviewFragment extends Fragment {
    private TextView mDescriptionTv;
    private TextView mPriceTv;
    private TextView mParticipants;
    private Workshop workshop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_overview, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_overview_tv_description);
        mPriceTv = root.findViewById(R.id.fragment_workshop_overview_tv_price);
        mParticipants = root.findViewById(R.id.fragment_worksjop_overview_tv_details);

        mParticipants.setText("60 minuten | Maximaal " + workshop.getMaxParticipants() + " deelnemers");
        mPriceTv.setText(workshop.getPrice() + ",-");
        mDescriptionTv.setText(workshop.getDescription());

        return root;
    }

    public WorkshopOverviewFragment(Workshop workshop){
        this.workshop = workshop;
    }

}