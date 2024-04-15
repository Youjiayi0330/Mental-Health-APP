package com.example.mentalhealth.MyImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

public class CircularBeadImageView extends AppCompatImageView {
    float width,height;
    //此值代表圆角的半径
    int angle = 20;

    public CircularBeadImageView(Context context) {
        this(context, null);
    }

    public CircularBeadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircularBeadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Android4.0及之前的手机中，因为硬件加速等原因，在使用clipPath时很有可能 会发生UnsupportedOperationException异常
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //主要为了防止屏幕宽高小于圆角半径值这种诡异的现象出现
        if (width > angle && height > angle) {
            Path path = new Path();
            path.moveTo(angle, 0);
            path.lineTo(width - angle, 0);
            path.quadTo(width, 0, width, angle);
            path.lineTo(width, height - angle);
            path.quadTo(width, height, width - angle, height);
            path.lineTo(angle, height);
            path.quadTo(0, height, 0, height - angle);
            path.lineTo(0, angle);
            path.quadTo(0, 0, 40, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
