package com.wce.tractorapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundedImageView extends AppCompatImageView {
    private Path path;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.addRoundRect(new RectF(0,0,w,h),dpToPx(8), dpToPx(8), Path.Direction.CW);
        path.addOval(new RectF(0,0,w,h), Path.Direction.CW);


    }

    private float dpToPx(int i) {
        return getResources().getDisplayMetrics().density*i;
    }
}
