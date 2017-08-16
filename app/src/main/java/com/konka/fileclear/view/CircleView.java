package com.konka.fileclear.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by user001 on 2017-8-16.
 */

public class CircleView extends ViewGroup {
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private Path path = new Path();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // compute the path
        float halfWidth = w / 2f;
        float halfHeight = h / 2f;
        float centerX = halfWidth;
        float centerY = halfHeight;
        path.reset();
        path.addCircle(centerX, centerY, Math.min(halfWidth, halfHeight), Path.Direction.CW);
        path.close();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }
}
