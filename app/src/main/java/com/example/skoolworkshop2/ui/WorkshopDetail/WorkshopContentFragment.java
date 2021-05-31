package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;

public class WorkshopContentFragment extends Fragment {
    private Workshop workshop;
    private TextView mContentTv;
    public WorkshopContentFragment(Workshop workshop) {
       this.workshop = workshop;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_content, container, false);
        mContentTv = root.findViewById(R.id.fragment_workshop_content_tv_txt);
        mContentTv.setText(workshop.getDescription()[1]);
        // Inflate the layout for this fragment
        return root;
    }
}