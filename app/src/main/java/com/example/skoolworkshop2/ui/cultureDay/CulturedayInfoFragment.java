package com.example.skoolworkshop2.ui.cultureDay;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;


public class CulturedayInfoFragment extends Fragment {
    private TextView mDescriptionTv;
    private Product cultureDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workshop_info, container, false);
        mDescriptionTv = root.findViewById(R.id.fragment_workshop_info_tv_txt);
        mDescriptionTv.setText(Html.fromHtml(cultureDay.getPracticalInformation(), Html.FROM_HTML_MODE_COMPACT));

        return root;
    }

    public CulturedayInfoFragment(Product cultureDay){
        this.cultureDay = cultureDay;
    }
}
