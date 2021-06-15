package com.example.skoolworkshop2.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.skoolworkshop2.R;

public class RoundedDialog extends Dialog {
    private TextView mHeaderTv;
    private TextView mContentTv;

    private String header;
    private String content;

    public RoundedDialog(@NonNull Context context, String header, String content) {
        super(context);

        this.header = header;
        this.content = content;

        getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_container));
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);

        mHeaderTv = findViewById(R.id.dialog_alert_tv_header);
        mContentTv = findViewById(R.id.dialog_alert_tv_content);

        mHeaderTv.setText(header);
        mContentTv.setText(content);

    }
}
