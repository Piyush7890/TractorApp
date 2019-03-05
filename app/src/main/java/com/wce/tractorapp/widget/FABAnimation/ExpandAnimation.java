package com.wce.tractorapp.widget.FABAnimation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;

public class ExpandAnimation extends BaseAnimation {


        public ExpandAnimation( int collapsedWidth, int expandedWidth, View view) {
            super(collapsedWidth,expandedWidth,view);
        }


        protected final void applyTransformation(float f, Transformation transformation) {
            int i;
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (f != 1.0f) {
                i = (int) (((float) this.collapsedWidth) + ((float)( this.expandedWidth-collapsedWidth) * f));
            } else {
                i = expandedWidth;
            }
            layoutParams.width = i;
            view.requestLayout();
        }
    
}
