package com.wce.tractorapp.ProductViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wce.tractorapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView productImage;
    public TextView title;
   public TextView location;
   public TextView price;
    public TextView type;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        location = itemView.findViewById(R.id.location);
        price = itemView.findViewById(R.id.price);
        type = itemView.findViewById(R.id.type);



    }
}