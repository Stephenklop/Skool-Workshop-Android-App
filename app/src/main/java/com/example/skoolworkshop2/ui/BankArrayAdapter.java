package com.example.skoolworkshop2.ui;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.Workshop;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankArrayAdapter extends ArrayAdapter<Object> {
    LayoutInflater layoutInflater;
    private Context context;

    public BankArrayAdapter(@NonNull Context context, @NonNull List<Bank> bankList) {
        super(context, 0, (Object[]) bankList.toArray());
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_bank_spinner, parent, false);
        } else {
            view = convertView;
        }
        setBank(view, (Bank) getItem(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view;
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.item_bank_spinner_header, parent, false);
            view.setOnClickListener((View v) -> {
                View root = parent.getRootView();
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            });
        } else {
            view = layoutInflater.inflate(R.layout.item_bank_spinner_dropdown, parent, false);
            setBank(view, (Bank) getItem(position));
        }
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null;
        } else {
            return super.getItem(position - 1);
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    private void setBank(View view, Bank bank) {
        TextView bankTv = view.findViewById(R.id.item_bank_spinner_tv);
        ImageView bankImg = view.findViewById(R.id.item_bank_spinner_img_bank);

        if (bank != null) {
            bankTv.setText(bank.getName());
            Glide.with(context).load(bank.getSvg()).into(bankImg);
        } else {
            bankImg.setImageDrawable(null);
            bankTv.setText("Kies een bank");
        }
    }
}
