package com.wce.tractorapp.widget.FABAnimation;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Transformation;

public class CollapseAnimation extends BaseAnimation
{



    public CollapseAnimation(int collapsedWidth, int expandedWidth, View view) 
    {
        super(collapsedWidth, expandedWidth, view);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        LayoutParams params = view.getLayoutParams();
        if(interpolatedTime!=1.0f)
        params.width = ((int) (expandedWidth - (expandedWidth - collapsedWidth) * interpolatedTime));
        else {
            params.width = collapsedWidth;
        }
        view.requestLayout();
    }
}
