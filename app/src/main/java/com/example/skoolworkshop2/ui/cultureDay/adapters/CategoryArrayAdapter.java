package com.example.skoolworkshop2.ui.cultureDay.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skoolworkshop2.R;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final Activity mActivity;
    private List<String> mWorkshopNames;

    public CategoryArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> workshopNames) {
        super(context, resource, workshopNames);

        mContext = context;
        mActivity = (Activity) mContext;
        mWorkshopNames = workshopNames;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_dropdown, parent, false);
        }

        setWorkshop(convertView, getItem(position));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_header, parent, false);
            convertView.setOnClickListener((View v) -> {
                View root = parent.getRootView();
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            });
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_dropdown, parent, false);
            setWorkshop(convertView, getItem(position));
        }

        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        if (position == 0) {
            return null;
        } else {
            return mWorkshopNames.get(position - 1);
        }
    }

    @Override
    public int getCount() {
        return mWorkshopNames.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setWorkshop(View view, String category) {
        TextView workshopTv = view.findViewById(R.id.item_spinner_tv);
        if (category != null) {
            workshopTv.setText(category);
        } else {
            workshopTv.setText("Kies een categorie");
        }
    }

    public void updateData(List<String> workshops) {
        mWorkshopNames = workshops;
    }

    public void refreshList() {
        mActivity.runOnUiThread(this::notifyDataSetChanged);
    }
}
