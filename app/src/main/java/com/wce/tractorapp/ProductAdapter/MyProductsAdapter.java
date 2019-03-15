package com.wce.tractorapp.ProductAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.ProductViewHolder.MyProductsViewHolder;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import androidx.annotation.NonNull;

public class MyProductsAdapter extends ProductsAdapter<MyProductsViewHolder> {

    MoreOptionListener moreOptionListener;
    public MyProductsAdapter(@NonNull FirebaseRecyclerOptions<RenterData> options, OnProductClickedListener listener,  MoreOptionListener moreOptionListener) {
        super(options, listener);
        this.moreOptionListener = moreOptionListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyProductsViewHolder productViewHolder, int i, @NonNull RenterData renterData) {
        super.onBindViewHolder(productViewHolder, i, renterData);
        productViewHolder.moreOption.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public MyProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false), moreOptionListener);

    }

   public interface MoreOptionListener
    {
        void onDeleteProduct(int id);
        void onChangePrice(int id);
        void onChangeStatus(int id);
    }


}
