package com.wce.tractorapp.ItemAnimators;


import android.view.View;

import com.wce.tractorapp.widget.Utils;


public class SlideUpAlphaAnimator extends SlideAlphaAnimator<SlideUpAlphaAnimator> {
    protected float getAnimationTranslationX(View view) {
        return 0.0f;
    }

    public static SlideUpAlphaAnimator create() {
        return new SlideUpAlphaAnimator()
                .withInterpolator(Utils.getFastOutSlowIn())
                .withAddDuration(180)
                .withRemoveDuration(180);
    }

    public SlideUpAlphaAnimator() {
        super(0);
    }

    protected float getAnimationTranslationY(View view) {
        return view.getHeight()*0.9f;
    }
}