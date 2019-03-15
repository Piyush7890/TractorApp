package com.wce.tractorapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.wce.tractorapp.ProductViewHolder.ProductViewHolder;
import com.wce.tractorapp.model.RenterData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultsAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    public SearchResultsAdapter(Listener listener) {
        this.listener = listener;
    }

    private Listener listener;

    public List<RenterData> getRenterData() {
        return results;
    }

    public void setRenterData(List<RenterData> renterData) {
        this.results = renterData;
        notifyItemRangeChanged(0, results.size());
    }

    public void clearData()
    {
        if(results.isEmpty())
            return;
        this.results.clear();
        notifyDataSetChanged();
    }

    List<RenterData> results = new ArrayList<>();



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        final RenterData renterData = results.get(position);
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

    @Override
    public int getItemCount() {
        return results.size();
    }

    public interface Listener
    {
        void onProductClicked(RenterData renterData);
    }

}
