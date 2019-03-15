package com.wce.tractorapp.main;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wce.tractorapp.R;
import com.wce.tractorapp.SearchActivity;
import com.wce.tractorapp.ToolBarActivity;
import com.wce.tractorapp.main.controller.BottomNavController;
import com.wce.tractorapp.model.Sort;
import com.wce.tractorapp.widget.Utils;

import androidx.fragment.app.Fragment;

public class MainActivity extends ToolBarActivity implements BottomNavController.Listener, FilterDialogFragment.OnOptionSelectedListener {

    FilterDialogFragment.FilterOption selectedOption = FilterDialogFragment.FilterOption.OLDEST_FIRST;

    ViewGroup container;
    AppBarLayout appBarLayout;
    TextView title;
    BottomNavigationView navigationView;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Utils.setLightStatusBar(this, Color.WHITE);
        selectedFragment = new ExploreFragment();
        BottomNavController.replaceFragment(this, selectedFragment);
         navigationView =  findViewById(R.id.bottom_nav_bar);
         new BottomNavController(navigationView, this,this);
        container = findViewById(R.id.container);
        appBarLayout = findViewById(R.id.appbar);
        title  = findViewById(R.id.title);
         ViewTreeObserver vto = container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //container.setPadding(container.getPaddingStart(),container.getPaddingTop()+appBarLayout.getHeight(),container.getPaddingEnd(),container.getPaddingBottom());
                container.setTranslationY(-appBarLayout.getHeight());
                container.getLayoutParams().height = container.getHeight()+appBarLayout.getHeight();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected boolean isBackEnabled() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onFragmentClicked(int id, Fragment currentFragment) {
        String text;
        invalidateOptionsMenu();
        switch (id)
        {
            case R.id.action_chats:
                text="Chats";
                break;

            case R.id.action_explore:
                text="Explore";
                break;
            case R.id.action_my_ads:
                text="My Ads";
                break;
            case  R.id.action_profile:
                text = "My Profile";
                break;
                default:
                    text="Explore";
        }
        final String finalText = text;
        title.animate().alpha(0f).setInterpolator(new DecelerateInterpolator()).setDuration(75L).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                title.setText(finalText);
                title.animate().alpha(1f).setDuration(180L).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        title.setAlpha(1f);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                title.setText(finalText);
                title.setAlpha(1f);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.filter:
                Bundle args = new Bundle();
                args.putInt("filterOption", selectedOption.getValue());
            FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance();
                filterDialogFragment.setArguments(args);
                filterDialogFragment.setListener(this);

                filterDialogFragment.show(getSupportFragmentManager(),
                    "filter_dialog_fragment");
            return  true;
            case R.id.search:
            {
                startActivity(new Intent(this, SearchActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return true;
            }
        }
        return  false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (navigationView.getSelectedItemId())
        {
            case R.id.action_explore:
                menu.findItem(R.id.search).setVisible(true);
                menu.findItem(R.id.filter).setVisible(true);
                break;
            case R.id.action_my_ads:
                menu.findItem(R.id.search).setVisible(true);
                menu.findItem(R.id.filter).setVisible(false);
                break;
            case R.id.action_chats:
                menu.findItem(R.id.search).setVisible(false);
                menu.findItem(R.id.filter).setVisible(false);
                break;
            case R.id.action_profile:
                menu.findItem(R.id.search).setVisible(false);
                menu.findItem(R.id.filter).setVisible(false);
                break;

        }
        return true;
    }

    @Override
    public void onSortOptionSelected(Sort sort) {
        if(selectedFragment instanceof ExploreFragment)
        {
            ((ExploreFragment) selectedFragment).sort(sort);
        }
    }

    @Override
    public void onLocationSelected(String location) {

    }

    @Override
    public void onTypeSelected(String type) {

    }
}
