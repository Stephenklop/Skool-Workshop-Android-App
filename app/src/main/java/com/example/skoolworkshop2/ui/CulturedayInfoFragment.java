package com.example.skoolworkshop2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.CultureDay;


public class CulturedayInfoFragment extends Fragment {
    private TextView mDescriptionTv;
    private CultureDay cultureDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_info, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_info_tv_txt);
        mDescriptionTv.setText(cultureDay.getDescription()[2]);

        return root;
    }

    public CulturedayInfoFragment(CultureDay cultureDay){
        this.cultureDay = cultureDay;
    }
}
