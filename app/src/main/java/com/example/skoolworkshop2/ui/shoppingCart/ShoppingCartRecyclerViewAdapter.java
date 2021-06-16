package com.example.skoolworkshop2.ui.shoppingCart;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.ProductItem;

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

        Glide.with(context).load(product.getSourceImage()).centerCrop().into(holder.mWorkshopImage);
        holder.mWorkshopTitle.setText(product.getName());
        holder.mWorkshopPrice.setText("€" + String.format("%.2f", shoppingCartItems.get(position).getTotalPrice()).replace(".", ","));
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
        LinearLayout mDetails;
        int height;

        public ViewHolder(View itemView) {
            super(itemView);

            mWorkshopImage = itemView.findViewById(R.id.item_shopping_cart_img_workshop);
            mWorkshopTitle = itemView.findViewById(R.id.item_shopping_cart_tv_workshop);
            mWorkshopPrice = itemView.findViewById(R.id.item_shopping_cart_tv_price);
            mDetailButton = itemView.findViewById(R.id.item_shopping_cart_btn_details);
            mDetails = itemView.findViewById(R.id.item_shopping_cart_ll_details);

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