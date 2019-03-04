package com.wce.tractorapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.SignUpData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollapsibleAddressCard extends CollapsibleCard {

    TextInputEditText address, state, city;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        address = findViewById(R.id.address_et);
        city = findViewById(R.id.city_et);
        state = findViewById(R.id.state_et);

    }

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

    @Override
    public SignUpData getDetails(SignUpData signUpData) {
        signUpData.setAddress(address.getText().toString());
        signUpData.setCity(city.getText().toString());
        signUpData.setState(state.getText().toString());
        return signUpData;
    }
}
