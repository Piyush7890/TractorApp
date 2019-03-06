package com.wce.tractorapp.widget;

import android.app.Activity;
import android.os.Build;
import android.view.View;
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

    public static void setLightStatusBar(Activity context, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            context.getWindow().setStatusBarColor(color);
            final View view = context.getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }
}
