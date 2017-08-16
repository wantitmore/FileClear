package com.konka.fileclear.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * Created by user001 on 2017-8-16.
 */

public class CircleLayout extends RelativeLayout {


        private Bitmap mBitmap;
        private Canvas mCanvas;
        private RectF mBounds;

        public CircleLayout(Context context) {
            super(context);
        }

        public CircleLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if (w != oldw && h != oldh) {
                mBounds = new RectF(0, 0, w, h);
                mBitmap = Bitmap.createBitmap((int) mBounds.width(), (int) mBounds.height(), Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
            }
        }

        @Override
        protected void dispatchDraw(@NonNull Canvas canvas) {
            // clear the canvas and save draw a new one one the bitmap
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            super.dispatchDraw(mCanvas);

            BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(shader);

            canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mBounds.width() / 2, paint);
        }
}
