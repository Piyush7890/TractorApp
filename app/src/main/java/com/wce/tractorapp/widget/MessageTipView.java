package com.wce.tractorapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wce.tractorapp.R;

import androidx.annotation.Nullable;

public class MessageTipView extends View {
Paint paint;
private float largeRadius, smallRadius, topOffset;
Path leftPath, rightPath;
    private int type = 0;
    public MessageTipView(Context context) {
        super(context);
    }

    public MessageTipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageTipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attributeSet,R.styleable.MessageTipView);
        if (array.hasValue(R.styleable.MessageTipView_type)) {
             type = array.getInt(R.styleable.MessageTipView_type, 0);
        }
            paint.setColor(array.getColor(R.styleable.MessageTipView_color,Color.parseColor("#000000")));
        array.recycle();
        leftPath = new Path();
        rightPath = new Path();
        paint.setStyle(Paint.Style.FILL);
        smallRadius = getResources().getDimensionPixelSize(R.dimen.messageSmallRadius);
        largeRadius = getResources().getDimensionPixelSize(R.dimen.messageLargeRadius);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        switch (type)
        {
            case 0:
                canvas.drawPath(leftPath,paint);
                break;
            case 1:
                canvas.drawPath(rightPath,paint);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topOffset = smallRadius+smallRadius/2;
//        rightPath.moveTo(w,0);
//        rightPath.lineTo(w,h-largeRadius);
//        rightPath.arcTo(new RectF(w-2*largeRadius,h-2*largeRadius,w,h),0,90);
//        rightPath.lineTo(largeRadius,h);
//        rightPath.arcTo(new RectF(0,h-2*largeRadius,2*largeRadius,h),90,90);
//        rightPath.lineTo(0,largeRadius+smallRadius);
//        rightPath.arcTo(new RectF(0,0+topOffset,2*largeRadius,2*largeRadius+smallRadius),180,90);
//        rightPath.lineTo(w-2*smallRadius,topOffset);
//        rightPath.arcTo(new RectF(w-2*largeRadius,-topOffset,w,topOffset),90,-90);
//        rightPath.close();

        rightPath.moveTo(0,topOffset);
        rightPath.arcTo(new RectF(0,-topOffset,w,h),90,-90);
        rightPath.lineTo(w,topOffset);
        rightPath.close();


        leftPath.moveTo(0,0);
        leftPath.arcTo(new RectF(0,-topOffset,w,h),180,-90);
        leftPath.lineTo(0,h);
        leftPath.close();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(MeasureSpec.makeMeasureSpec(((int) largeRadius*2), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(((int) (smallRadius+smallRadius/2)), MeasureSpec.EXACTLY));
    }
}
