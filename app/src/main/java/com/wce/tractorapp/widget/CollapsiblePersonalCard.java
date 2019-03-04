package com.wce.tractorapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.SignUpData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollapsiblePersonalCard extends CollapsibleCard {
    public CollapsiblePersonalCard(@NonNull Context context) {
        super(context);
    }
TextInputEditText contact, name, adhaar;
    public CollapsiblePersonalCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsiblePersonalCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contact = findViewById(R.id.contact_et);
        name = findViewById(R.id.name_et);
        adhaar = findViewById(R.id.adhaar_et);
    }

    @Override
    int getLayoutId() {
        return R.layout.personal_details;
    }

    @Override
    public SignUpData getDetails(SignUpData signUpData) {
        signUpData.setContactNo(contact.getText().toString());
        signUpData.setAadharNo(adhaar.getText().toString());
        signUpData.setName(name.getText().toString());
        return signUpData;
    }


}
