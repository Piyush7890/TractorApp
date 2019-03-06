package com.wce.tractorapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.wce.tractorapp.model.RenterData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends FirebaseRecyclerAdapter<RenterData, ProductsAdapter.ProductViewHolder> {


    private OnProductClickedListener listener;

    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<RenterData> options, OnProductClickedListener listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final RenterData renterData) {
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
        productViewHolder.title.setText(renterData.getTitle());
        productViewHolder.type.setText(renterData.getEquipmentType());
        productViewHolder.price.setText(String.format("%s per month", String.valueOf(renterData.getRent())));
        productViewHolder.location.setText(renterData.getCity());

    }



    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView title;
        TextView location;
        TextView price;
        TextView type;


        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            type = itemView.findViewById(R.id.type);

        }
    }

    public  interface OnProductClickedListener
    {
        void onProductClicked(RenterData renterData);
    }
}
