package com.wce.tractorapp.widget;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

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



    public static List<View> getAllDescendants(ViewGroup viewGroup) {
        List<View> arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        arrayList2.add(viewGroup);
        while (!arrayList2.isEmpty()) {
            View view = (View) arrayList2.remove(0);
            arrayList.add(view);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup2 = (ViewGroup) view;
                int childCount = viewGroup2.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    arrayList2.add(viewGroup2.getChildAt(i));
                }
            }
        }
        return arrayList;
    }


    public static int getRelativeTop(View view, ViewGroup viewGroup) {
        if (view.getParent() == viewGroup) {
            return view.getTop();
        }
        return getRelativeTop((View) view.getParent(), viewGroup) + view.getTop();
    }

    public static int getRelativeLeft(View view, ViewGroup viewGroup) {
        if (view.getParent() == viewGroup) {
            return view.getLeft();
        }
        return getRelativeLeft((View) view.getParent(), viewGroup) + view.getLeft();
    }

}
