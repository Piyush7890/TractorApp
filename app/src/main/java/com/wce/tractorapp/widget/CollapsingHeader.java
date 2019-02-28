package com.wce.tractorapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wce.tractorapp.R;

import androidx.annotation.Nullable;

public class CollapsingHeader extends LinearLayout {

    private String title;


    public CollapsingHeader(Context context) {
        super(context);
    }

    public CollapsingHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CollapsingHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.CollapsingHeader, 0, 0);
        title = arr.getString(R.styleable.CollapsingHeader_title);
        arr.recycle();
        LayoutInflater.from(getContext()).inflate(R.layout.collapsible_header, this, true);
        ((TextView) findViewById(R.id.title)).setText(title);
    }
}
