package com.example.skoolworkshop2.ui.cultureDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;

public class CulturedayContentFragment extends Fragment {
    private Product cultureDay;
    private TextView mContentTv;

    public CulturedayContentFragment(Product cultureDay){
        this.cultureDay = cultureDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_content, container, false);
        mContentTv = root.findViewById(R.id.fragment_workshop_content_tv_txt);
        mContentTv.setText("");
        // Inflate the layout for this fragment
        return root;
    }
}
