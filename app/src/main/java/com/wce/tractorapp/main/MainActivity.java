package com.wce.tractorapp.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wce.tractorapp.R;
import com.wce.tractorapp.main.controller.BottomNavController;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavController.replaceFragment(this, new ExploreFragment());
         new BottomNavController(((BottomNavigationView) findViewById(R.id.bottom_nav_bar)), this);
    }
}
