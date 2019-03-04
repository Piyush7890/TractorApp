package com.wce.tractorapp.main.controller;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wce.tractorapp.R;
import com.wce.tractorapp.main.ChatsFragment;
import com.wce.tractorapp.main.ExploreFragment;
import com.wce.tractorapp.main.MyAdsFragment;
import com.wce.tractorapp.main.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class BottomNavController implements BottomNavigationView.OnNavigationItemSelectedListener, LifecycleObserver {

    private  AppCompatActivity activity;
    private BottomNavigationView bottomNavigationView;

    public BottomNavController(BottomNavigationView bottomNavigationView, AppCompatActivity activity) {
        this.bottomNavigationView = bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.activity  = activity;
        activity.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy()
    {
      bottomNavigationView.setOnNavigationItemSelectedListener(null);
      activity = null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.action_chats:
            {
                replaceFragment(activity, new ChatsFragment());
                break;
            }
            case R.id.action_explore:
            {
                replaceFragment(activity, new ExploreFragment());
                break;
            }
            case R.id.action_my_ads:
            {
                replaceFragment(activity, new MyAdsFragment());
                break;
            }
            case R.id.action_profile:
            {
                replaceFragment(activity, new ProfileFragment());
                break;
            }
        }
        return true;
    }

    public final static  void replaceFragment( AppCompatActivity appCompatActivity,  Fragment fragment) {

        FragmentTransaction fragmentTransaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_up, R.anim.fragment_fade_out);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
