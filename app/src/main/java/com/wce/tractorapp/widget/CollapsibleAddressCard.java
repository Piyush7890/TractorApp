package com.wce.tractorapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.wce.tractorapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollapsibleAddressCard extends CollapsibleCard {
    public CollapsibleAddressCard(@NonNull Context context) {
        super(context);
    }

    public CollapsibleAddressCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleAddressCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    int getLayoutId() {
        return R.layout.address_details;
    }
}
