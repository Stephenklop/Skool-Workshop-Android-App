package com.example.skoolworkshop2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class CountryArrayAdapter extends ArrayAdapter<Drawable> {
    LayoutInflater layoutInflater;

    public CountryArrayAdapter(@NonNull Context context, @NonNull Object[] objects) {
        super(context, 0, (Drawable[]) objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_country_spinner, parent, false);
        } else {
            view = convertView;
        }
        setWorkshop(view, getItem(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view;
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.item_country_spinner_header, parent, false);
            view.setOnClickListener((View v) -> {
                View root = parent.getRootView();
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            });
        } else {
            view = layoutInflater.inflate(R.layout.item_country_spinner_dropdown, parent, false);
            setWorkshop(view, getItem(position));
        }
        return view;
    }

    @Nullable
    @Override
    public Drawable getItem(int position) {
        if (position == 0) {
            return getContext().getDrawable(R.drawable.ic_transparent);
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

    private void setWorkshop(View view, Drawable flag) {
        ImageView countryIv = view.findViewById(R.id.item_country_spinner_iv);
        countryIv.setImageDrawable(flag);
    }
}
