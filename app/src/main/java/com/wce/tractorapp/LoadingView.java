package com.wce.tractorapp;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.Collection;

public class LoadingView extends FrameLayout {
    public static final LinearInterpolator INTERPOLATOR = new LinearInterpolator();
    public AnimatorSet animatorSet;
    public Collection<View> children;
    public boolean stopAnimationAfterCurrentRun;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postOnAnimationDelayed(new LoadingAnimation(this), 250);
    }

    protected void onDetachedFromWindow() {
        this.stopAnimationAfterCurrentRun = true;
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
        }
        super.onDetachedFromWindow();
    }
}