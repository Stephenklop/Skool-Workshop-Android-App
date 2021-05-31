package com.example.skoolworkshop2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.CultureDay;

public class CulturedayContentFragment extends Fragment {
    private CultureDay cultureDay;
    private TextView mContentTv;

    public CulturedayContentFragment(CultureDay cultureDay){
        this.cultureDay = cultureDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_content, container, false);
        mContentTv = root.findViewById(R.id.fragment_workshop_content_tv_txt);
        mContentTv.setText(cultureDay.getDescription()[1]);
        // Inflate the layout for this fragment
        return root;
    }
}
