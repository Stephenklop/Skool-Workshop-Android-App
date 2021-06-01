package com.example.skoolworkshop2;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class LayoutTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        RecyclerView rv = findViewById(R.id.activity_shopping_cart_rv);
        rv.setAdapter(new RecyclerView.Adapter() {
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                class ShoppingItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                    LinearLayout mDetailsLl;
                    Button mDetailBtn;
                    ImageView mWorkshopIv;
                    int height;

                    public ShoppingItemViewHolder(@NonNull @NotNull View itemView) {
                        super(itemView);
                        mDetailBtn = itemView.findViewById(R.id.item_shopping_cart_btn_details);
                        mDetailsLl = itemView.findViewById(R.id.item_shopping_cart_ll_details);
                        mWorkshopIv = itemView.findViewById(R.id.item_shopping_cart_iv_workshop);

                        mDetailBtn.setText("Details");
                        mDetailBtn.setOnClickListener(this);

                        mDetailsLl.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        height = mDetailsLl.getMeasuredHeight();

                        mDetailsLl.getLayoutParams().height = 0;

                        mWorkshopIv.setClipToOutline(true);
                    }

                    @Override
                    public void onClick(View v) {
                        if (mDetailBtn.getText().equals("Details")) {
                            mDetailBtn.setText("Verbergen");
                            animateDetails(mDetailsLl, 0, height);
                        } else {
                            mDetailBtn.setText("Details");
                            animateDetails(mDetailsLl, height,0);
                        }
                    }

                    private void animateDetails(View v, int currentValue, int newValue) {
                        ValueAnimator slideAnimator = ValueAnimator
                                .ofInt(currentValue, newValue)
                                .setDuration(300);

                        slideAnimator.addUpdateListener(animation -> {
                            Integer value = (Integer) animation.getAnimatedValue();
                            v.getLayoutParams().height = value.intValue();
                            v.requestLayout();
                        });

                        AnimatorSet set = new AnimatorSet();
                        set.play(slideAnimator);
                        set.setInterpolator(new AccelerateDecelerateInterpolator());
                        set.start();
                    }
                }

                return new ShoppingItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_shopping_cart, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(false);


    }
}
