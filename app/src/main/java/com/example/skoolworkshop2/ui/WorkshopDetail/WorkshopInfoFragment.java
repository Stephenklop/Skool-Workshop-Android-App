package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;

public class WorkshopInfoFragment extends Fragment {

    private TextView mDescriptionTv;
    private Workshop workshop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_info, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_info_tv_txt);
//        mDescriptionTv.setText(workshop.getDescription()[2]);

        return root;
    }

    public WorkshopInfoFragment(Workshop workshop){
        this.workshop = workshop;
    }
}
