package com.wce.tractorapp;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class LoginActivity extends AppCompatActivity  implements LoginFragment.OnFragmentInteractionListener{

    ViewGroup parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent = findViewById(R.id.parent);
        getSupportFragmentManager().beginTransaction().replace(R.id.parent, new LoginFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(int id) {
        switch (id)
        {
            case R.id.create_account_btn :
            {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.parent, new SignUpFragment()).addToBackStack("loginfragment").commit() ;
            }

        }
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        super.onBackPressed();
        else getSupportFragmentManager().popBackStack();
    }
}
