package com.konka.fileclear.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by user001 on 2017-8-27.
 */

public class ScaleRecyclerView extends RecyclerView
{
    private int mSelectedPosition=0;

    public ScaleRecyclerView(Context context) {
        super(context);
        init();
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //启用子视图排序功能
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition = getChildAdapterPosition(getFocusedChild());
        super.onDraw(c);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int position = mSelectedPosition;
        Log.d("ScaleRecyclerView", "position: " + position + ", i is " + i);
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                Log.d("ScaleRecyclerView", "getChildDrawingOrder: 1position--" + i);
                return position;
            }
            if (i == position) {
                Log.d("ScaleRecyclerView", "getChildDrawingOrder: lastOne -" + i);
                return childCount - 1;
            }
        }
        Log.d("ScaleRecyclerView", "getChildDrawingOrder: i--" + i);
        return i;
    }
}
