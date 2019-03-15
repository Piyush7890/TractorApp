package com.wce.tractorapp.ProductAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.ProductViewHolder.ProductViewHolder;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import androidx.annotation.NonNull;

public class AllProductsAdapter extends ProductsAdapter<ProductViewHolder> {

    public AllProductsAdapter(@NonNull FirebaseRecyclerOptions<RenterData> options, OnProductClickedListener listener) {
        super(options, listener);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }
}
