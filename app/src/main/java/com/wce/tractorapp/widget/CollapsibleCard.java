package com.wce.tractorapp.widget;

import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.SignUpData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class CollapsibleCard extends LinearLayout {
    private ViewGroup container;
    private ViewGroup header;
    private boolean expanded = false;
    private Transition toggle;

    public CollapsibleCard(@NonNull Context context) {
        super(context);
        init();
    }

    public CollapsibleCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CollapsibleCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        toggle = TransitionInflater.from(getContext())
                .inflateTransition(R.transition.info_card_toggle);
        LayoutInflater.from(getContext()).inflate(getLayoutId(),this,true);
        header = findViewById(R.id.header);
        header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        container = findViewById(R.id.container);
    }

    abstract int getLayoutId();
    public abstract SignUpData getDetails(SignUpData signUpData);

    public void toggle()
    {

        expanded = container.getVisibility() != VISIBLE;
        toggle.setDuration(expanded? 300L : 200L);
        TransitionManager.beginDelayedTransition(((ViewGroup) getParent()), toggle);
        container.setVisibility(expanded?VISIBLE:GONE);
    }

    public boolean isAnyTextFieldEmpty()
    {
        int flag = 0;
        int size = container.getChildCount();
        for(int i=0;i<size;i++)
        {
            if(container.getChildAt(i) instanceof TextInputLayout)
            {
                TextInputLayout textInputLayout = (TextInputLayout)container.getChildAt(i);
                TextInputEditText textInputEditText = ((TextInputEditText) textInputLayout.getChildAt(0));
                if(textInputEditText.getText().toString().isEmpty())
                {
                    flag = 1;
                    textInputLayout.setError("Please fill the field");
                }
            }
        }
       return flag == 1;
    }

}
