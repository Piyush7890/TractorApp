package com.wce.tractorapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.wce.tractorapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollapsiblePersonalCard extends CollapsibleCard {
    public CollapsiblePersonalCard(@NonNull Context context) {
        super(context);
    }

    public CollapsiblePersonalCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsiblePersonalCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutId() {
        return R.layout.personal_details;
    }
}
