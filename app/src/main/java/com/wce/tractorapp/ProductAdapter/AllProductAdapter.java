package com.wce.tractorapp.ProductAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.wce.tractorapp.ProductViewHolder.ProductViewHolder;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.RenterData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    public List<RenterData> getList() {
        return list;
    }

    public void setList(List<RenterData> list) {
        this.list = list;
    }

    private List<RenterData> list = new ArrayList<>();
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        final RenterData renterData = list.get(i);

            Log.d("BINDING", "onBindViewHolder: "+ i);
            if(renterData.getUrls()!=null)
                Glide
                        .with(productViewHolder
                                .itemView.getContext())
                        .load(renterData.getUrls()
                                .get(0))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(productViewHolder.productImage);

            if(!renterData.isAvailabilityStatus())
                productViewHolder.itemView.setAlpha(0.375f);
            productViewHolder.title.setText(renterData.getTitle());
            productViewHolder.type.setText(renterData.getEquipmentType());
            productViewHolder.price.setText(String.format("%s per month", String.valueOf(renterData.getRent())));
            productViewHolder.location.setText(renterData.getCity());

        }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
