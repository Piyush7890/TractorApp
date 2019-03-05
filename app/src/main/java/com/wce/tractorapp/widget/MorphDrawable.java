package com.wce.tractorapp.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class MorphDrawable extends Drawable {
    private Paint paint;
    private float cornerRadius;
    private Path path;
    private Rect rect;

    public MorphDrawable(@ColorInt int color, float cornerRadius) {
        this.cornerRadius = cornerRadius;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    float getCornerRadius() {
        return cornerRadius;
    }

    void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidateSelf();
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(@ColorInt int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(path!=null)
        canvas.drawPath(path,
                paint);

    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        if(rect!=null)
        outline.setRoundRect(rect, cornerRadius);
        else
            outline.setRoundRect(getBounds(), cornerRadius);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha() == 255 ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
    }

    public void updateBounds(float left, float top, float right, float bottom, float radius)
    {
        if (this.path == null) {
            this.path = new Path();
            this.rect = new Rect();

        }
        rect.set(Math.round(left),Math.round(top),Math.round(right),Math.round(bottom));
        this.cornerRadius = radius;
        this.path.reset();
        this.path.addRoundRect(new RectF(left,top,right,bottom), radius, radius, Path.Direction.CW);
        this.path.close();
        invalidateSelf();

    }

}
