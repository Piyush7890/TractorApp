package com.wce.tractorapp.widget.FABAnimation;

import android.view.View;
import android.view.animation.Animation;

class BaseAnimation extends Animation {

   protected final int collapsedWidth;
   protected final int expandedWidth;
   protected final View view;
   protected BaseAnimation(int collapsedWidth, int expandedWidth, View view)
   {

       this.collapsedWidth = collapsedWidth;
       this.expandedWidth = expandedWidth;
       this.view = view;
   }

   @Override
   public boolean willChangeBounds() {
       return true;
   }
}
