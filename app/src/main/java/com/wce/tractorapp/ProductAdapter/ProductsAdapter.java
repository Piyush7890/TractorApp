package com.wce.tractorapp.ProductAdapter;

import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.ProductViewHolder.ProductViewHolder;
import com.wce.tractorapp.model.RenterData;

import androidx.annotation.NonNull;

public  abstract  class ProductsAdapter<T extends ProductViewHolder> extends FirebaseRecyclerAdapter<RenterData, T> {


    private OnProductClickedListener listener;
    boolean reverse=false;

    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<RenterData> options, OnProductClickedListener listener) {
        super(options);
        this.listener = listener;
    }



    @Override
    protected void onBindViewHolder(@NonNull T productViewHolder, int i, @NonNull final RenterData renterData) {
        Log.d("BINDING", "onBindViewHolder: "+ i);
        if(renterData.getUrls()!=null)
            Glide
                    .with(productViewHolder
                            .itemView.getContext())
                    .load(renterData.getUrls()
                            .get(0))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(productViewHolder.productImage);
        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClicked(renterData);
            }
        });
        if(!renterData.isAvailabilityStatus())
            productViewHolder.itemView.setAlpha(0.375f);
        productViewHolder.title.setText(renterData.getTitle());
        productViewHolder.type.setText(renterData.getEquipmentType());
        productViewHolder.price.setText(String.format("%s per month", String.valueOf(renterData.getRent())));
        productViewHolder.location.setText(renterData.getCity());

    }





    public  interface OnProductClickedListener
    {
        void onProductClicked(RenterData renterData);
    }
    @Override
    public RenterData getItem(int position) {
        if(reverse)
        return super.getItem(getItemCount() - 1 - position);
        return super.getItem(position);
    }


    public void setReverse(boolean reverse)
    {
        if(this.reverse==reverse)
            return;
        this.reverse=reverse;
        notifyDataSetChanged();
    }

    public class AllProductsViewHolder extends ProductViewHolder {
        AllProductsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
