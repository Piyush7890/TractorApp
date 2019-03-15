package com.wce.tractorapp.ProductViewHolder;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wce.tractorapp.ProductAdapter.MyProductsAdapter;
import com.wce.tractorapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

public class MyProductsViewHolder extends ProductViewHolder {
   public ImageView moreOption;
    public MyProductsViewHolder(@NonNull View itemView, final MyProductsAdapter.MoreOptionListener listener) {
        super(itemView);
        moreOption = itemView.findViewById(R.id.more_option);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(v.getContext(), R.style.PopUpMenu);

                PopupMenu popup = new PopupMenu(wrapper, v);
                //inflating menu from xml resource
                popup.inflate(R.menu.edit_ad_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_ad:
                                listener.onDeleteProduct(getAdapterPosition());
                                return true;
                            case R.id.change_price:
                                listener.onChangePrice(getAdapterPosition());
                                return true;
                            case R.id.change_status:
                                listener.onChangeStatus(getAdapterPosition());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();


            }
        });
    }
}
