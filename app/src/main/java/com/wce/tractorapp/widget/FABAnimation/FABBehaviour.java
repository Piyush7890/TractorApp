package com.wce.tractorapp.widget.FABAnimation;

import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import androidx.core.widget.NestedScrollView;

public class FABBehaviour implements NestedScrollView.OnScrollChangeListener {

    private final int touchSlop;
    private Animation collapseAnimation;
    private Animation expandAnimation;
    private View target;
    private int measuredWidth = -1;

    public FABBehaviour(View target) {
        //super();
        this.target = target;

        touchSlop = ViewConfiguration.get(target.getContext()).getScaledTouchSlop();

    }
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int currentWidth = target.getWidth();

        if(scrollY-oldScrollY<0)
        {
            if(collapseAnimation!=null) {

                collapseAnimation.cancel();
                collapseAnimation = null;
            }
            if(expandAnimation!=null)
                return;




            expandAnimation = new ExpandAnimation(currentWidth,measuredWidth,target);
            expandAnimation.setDuration(((long) ((int)((float)measuredWidth / target.getResources().getDisplayMetrics().density))));
            expandAnimation.setInterpolator(new DecelerateInterpolator());
            target.startAnimation(expandAnimation);
        }

        else if(scrollY-oldScrollY>0) {
            if(expandAnimation!=null) {
                expandAnimation.cancel();
                expandAnimation = null;

            }
            if(collapseAnimation!=null)
                return;

            if(measuredWidth<=0)
                measuredWidth = target.getMeasuredWidth();
            collapseAnimation = new CollapseAnimation(target.getHeight(), target.getWidth(), target);
            collapseAnimation.setDuration(((long) (currentWidth / target.getResources().getDisplayMetrics().density)));
            collapseAnimation.setInterpolator(new DecelerateInterpolator());
            target.startAnimation(collapseAnimation);



        }


    }
}
