package com.wce.tractorapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.wce.tractorapp.model.SignUpData;
import com.wce.tractorapp.widget.CollapsibleCard;

import androidx.fragment.app.Fragment;



public class SignUpFragment extends Fragment implements View.OnClickListener {

    private CollapsibleCard loginDetails, addressDetails, personalDetails;
    private MaterialButton login, signUp;
    private onSignUp listener;


    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        addressDetails = view.findViewById(R.id.address_details);
        personalDetails = view.findViewById(R.id.personal_details);
        loginDetails = view.findViewById(R.id.login_details);
        login = view.findViewById(R.id.back_btn);
        signUp = view.findViewById(R.id.signup_btn);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        return view;
    }

    private boolean isAnyFieldEmpty()
    {
        return (addressDetails.isAnyTextFieldEmpty() || personalDetails.isAnyTextFieldEmpty() || loginDetails.isAnyTextFieldEmpty());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = ((onSignUp) getActivity());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                break;
            case R.id.signup_btn:
            {
                SignUpData signupdata = new SignUpData();
                addressDetails.getDetails(signupdata);
                personalDetails.getDetails(signupdata);
                loginDetails.getDetails(signupdata);
                listener.signUp(signupdata);
                break;
            }
        }
    }


    public interface  onSignUp
    {
        void signUp(SignUpData signUpData);
    }
}
