package com.wce.tractorapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.SignUpData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollapsibleLoginCard extends CollapsibleCard {
    public CollapsibleLoginCard(@NonNull Context context) {
        super(context);
    }

    public CollapsibleLoginCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleLoginCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutId() {
        return R.layout.login_details;
    }
TextInputEditText email,password,confirmPassword;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        confirmPassword = findViewById(R.id.confirm_password_et);
    }

    @Override
    public SignUpData getDetails(SignUpData signUpData) {
        signUpData.setEmail(email.getText().toString());
        signUpData.setPassword(password.getText().toString());
        return signUpData;
    }
}
