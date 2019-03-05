package com.wce.tractorapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClippableFrameLayout extends FrameLayout {
    Path path;
    Paint paint;
    public ClippableFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ClippableFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClippableFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init()
    {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(path==null)
        {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }



    public void updateBounds(float left, float top, float right, float bottom, float radius)
    {
        if (this.path == null) {
            this.path = new Path();
        }
        this.path.reset();
        this.path.addRoundRect(new RectF(left,top,right,bottom), radius, radius, Path.Direction.CW);
        this.path.close();
        invalidate();
    }
}
