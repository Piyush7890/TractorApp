package com.wce.tractorapp.widget;

import android.view.animation.Interpolator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class Utils {

    private static FastOutSlowInInterpolator interpolator;

    public static Interpolator getFastOutSlowIn()
    {
        if(interpolator==null)
            interpolator = new FastOutSlowInInterpolator();
        return interpolator;
    }
}
