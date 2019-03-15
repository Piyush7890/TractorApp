package com.wce.tractorapp.ProductAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.ProductViewHolder.ProductViewHolder;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import androidx.annotation.NonNull;

public class SelectProductAdapter extends AllProductsAdapter {
    private final Drawable drawable;

    public SelectProductAdapter(@NonNull FirebaseRecyclerOptions<RenterData> options, OnProductClickedListener listener, Context context) {

        super(options, listener);
     drawable = context.getResources().getDrawable(R.drawable.item_bg_ripple);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull RenterData renterData) {
        super.onBindViewHolder(productViewHolder, i, renterData);
        productViewHolder.itemView.setBackground(drawable);
    }
}
