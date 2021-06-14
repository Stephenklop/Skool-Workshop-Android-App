package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;

public class WorkshopCostFragment extends Fragment {

        private TextView mDescriptionTv;
        private Product workshop;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_workshop_costs, container, false);
            mDescriptionTv = root.findViewById(R.id.fragment_workshop_costs_tv_txt);
            // TODO: Add text
            mDescriptionTv.setText(Html.fromHtml(workshop.getCostsInfo(), Html.FROM_HTML_MODE_COMPACT));

            return root;
        }

        public WorkshopCostFragment(Product workshop){
            this.workshop = workshop;
        }
}
