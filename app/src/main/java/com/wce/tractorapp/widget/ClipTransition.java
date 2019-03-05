package com.wce.tractorapp.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class ClipTransition extends Transition {
    private static final String PROP_BOUNDS = "materialTransition:bounds";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;

        transitionValues.values.put(PROP_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                view.getRight(), view.getBottom()));
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        boolean fromFAB = startValues.view.getWidth()<endValues.view.getWidth();
        Rect initialBounds = ((Rect) startValues.values.get(PROP_BOUNDS));
        Rect finalBounds = ((Rect) endValues.values.get(PROP_BOUNDS));
        if(fromFAB)
        return ViewAnimationUtils.createCircularReveal(endValues.view, initialBounds.left+initialBounds.width()/2, initialBounds.top+initialBounds.height()/2, initialBounds.width(), (float) Math.hypot(((double) (initialBounds.top + initialBounds.height() / 2)), ((double) (initialBounds.left + initialBounds.width() / 2))));
        else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(endValues.view, "alpha", 1f, 0f).setDuration(200L);
            animator.setInterpolator(new FastOutSlowInInterpolator());
        return animator;
        }
    }
}
