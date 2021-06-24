package com.example.skoolworkshop2.ui.shoppingCart;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Product;

import java.util.List;

public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ViewHolder> {
    List<ShoppingCartItem> shoppingCartItems;
    Context context;

    public ShoppingCartRecyclerViewAdapter(List<ShoppingCartItem> shoppingCartItems, Context context) {
        this.shoppingCartItems = shoppingCartItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_cart, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = loadAssociatedProduct(shoppingCartItems.get(position).getProductId());
        ShoppingCartItem shoppingCartItem = shoppingCartItems.get(position);

        Glide.with(context).load(product.getSourceImage()).centerCrop().into(holder.mWorkshopImage);
        holder.mWorkshopTitle.setText(product.getName());
        holder.mWorkshopPrice.setText("€" + String.format("%.2f", shoppingCartItems.get(position).getRegularPrice()).replace(".", ","));
        holder.mDetailButton.setText("Details");
        holder.mDetailButton.setOnClickListener(v -> {
            if (holder.mDetailButton.getText().equals("Details")) {
                holder.mDetailButton.setText("Verbergen");
                animateDetails(holder.mDetails, 0, holder.height);
            } else {
                holder.mDetailButton.setText("Details");
                animateDetails(holder.mDetails, holder.height,0);
            }
        });
        holder.mCloseBtn.setOnClickListener(v -> {
            LocalDb.getDatabase(context).getShoppingCartDAO().deleteOneItemFromShoppingCart(shoppingCartItems.get(position).getId());
            shoppingCartItems.remove(position);
            notifyDataSetChanged();
        });

        holder.mParticipantsTv.setText("Totaal aantal deelnemers: " + shoppingCartItem.getParticipants());
        holder.mRoundsTv.setText("Aantal workshoprondes: " + shoppingCartItem.getRounds());
        holder.mRoundMinsTv.setText("Aantal minuten per workshopronde: " + shoppingCartItem.getRoundDuration());
        holder.mScheduleTv.setText("Tijdschema: " + shoppingCartItem.getTimeSchedule());
        holder.mLevelTv.setText("Leerniveau: " + shoppingCartItem.getLearningLevel());
        holder.mDateTv.setText("Workshopdatum: " + shoppingCartItem.getDate());
    }

    @Override
    public int getItemCount() {
        return shoppingCartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // One line Layout
        ImageView mWorkshopImage;
        TextView mWorkshopTitle;
        TextView mWorkshopPrice;
        Button mDetailButton;
        ImageButton mCloseBtn;
        LinearLayout mDetails;

        TextView mParticipantsTv;
        TextView mRoundsTv;
        TextView mRoundMinsTv;
        TextView mDurationTv;
        TextView mScheduleTv;
        TextView mLevelTv;
        TextView mDateTv;

        int height;

        public ViewHolder(View itemView) {
            super(itemView);

            mWorkshopImage = itemView.findViewById(R.id.item_shopping_cart_img_workshop);
            mWorkshopTitle = itemView.findViewById(R.id.item_shopping_cart_tv_workshop);
            mWorkshopPrice = itemView.findViewById(R.id.item_shopping_cart_tv_price);
            mDetailButton = itemView.findViewById(R.id.item_shopping_cart_btn_details);
            mCloseBtn = itemView.findViewById(R.id.item_shopping_cart_btn_close);
            mDetails = itemView.findViewById(R.id.item_shopping_cart_ll_details);

            mParticipantsTv = itemView.findViewById(R.id.item_shopping_cart_tv_participants);
            mRoundsTv = itemView.findViewById(R.id.item_shopping_cart_tv_rounds);
            mRoundMinsTv = itemView.findViewById(R.id.item_shopping_cart_tv_round_mins);
            mScheduleTv = itemView.findViewById(R.id.item_shopping_cart_tv_schedule);
            mLevelTv = itemView.findViewById(R.id.item_shopping_cart_tv_level);
            mDateTv = itemView.findViewById(R.id.item_shopping_cart_tv_date);

            mDetails.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            height = mDetails.getMeasuredHeight();
            mDetails.getLayoutParams().height = 0;

            mWorkshopImage.setClipToOutline(true);
        }
    }

    private void animateDetails(View view, int currentValue, int newValue) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(currentValue, newValue)
                .setDuration(300);

        slideAnimator.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();
            view.getLayoutParams().height = value.intValue();
            view.requestLayout();
        });

        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private Product loadAssociatedProduct(int id) {
        return LocalDb.getDatabase(context).getProductDAO().getProduct(id);
    }
}