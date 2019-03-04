package com.wce.tractorapp;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {

    private ViewGroup detailsContainer;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        appBarLayout = findViewById(R.id.appbar);
        detailsContainer = findViewById(R.id.details_container);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                float progress = Math.abs(i)/(float)appBarLayout.getTotalScrollRange();
//                if(detailsContainer.isLaidOut())
//                detailsContainer.setPaddingRelative(detailsContainer.getPaddingLeft(), ((int) (initialPadding + (finalPadding - initialPadding) * progress)),detailsContainer.getPaddingRight(), detailsContainer.getPaddingBottom());
//
//            }
//        });

    }



}
