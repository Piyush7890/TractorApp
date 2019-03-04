package com.wce.tractorapp.ItemAnimators;

import android.view.animation.Interpolator;

import com.wce.tractorapp.widget.Utils;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

public class PopItemAnimator extends DefaultItemAnimator<PopItemAnimator> {

    public static PopItemAnimator create() {
        PopItemAnimator a = new  PopItemAnimator()
                .withInterpolator(Utils.getFastOutSlowIn());
        a.setAddDuration(180);
        a.setRemoveDuration(180);
        return  a;
    }
    Interpolator interpolator = new FastOutSlowInInterpolator();
    @Override
    public void addAnimationPrepare(RecyclerView.ViewHolder holder) {
        super.addAnimationPrepare(holder);
        ViewCompat.setScaleX(holder.itemView, 0.75f);
        ViewCompat.setScaleY(holder.itemView, 0.75f);
    }

    @Override
    public Interpolator getInterpolator() {
        return interpolator;
    }

    @Override
    public void addAnimationCleanup(RecyclerView.ViewHolder holder) {
        super.addAnimationCleanup(holder);
        holder.itemView.setScaleX(1f);
        holder.itemView.setScaleY(1f);
    }

    @Override
    public ViewPropertyAnimatorCompat addAnimation(RecyclerView.ViewHolder holder) {
        return ViewCompat.animate(holder.itemView).alpha(1).scaleX(1f).scaleY(1f).setStartDelay(250L).setDuration(180).setInterpolator(getInterpolator());
    }
}
