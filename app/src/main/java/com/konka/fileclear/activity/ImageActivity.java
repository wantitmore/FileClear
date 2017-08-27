package com.konka.fileclear.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.ImageAdapter;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.entity.Image;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.util.List;

public class ImageActivity extends Activity {

    private static final String TAG = "ImageActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(ImageActivity.this, 5));
                    ImageAdapter imageAdapter = new ImageAdapter(ImageActivity.this, images);
//                    mRecyclerView.setChildDrawingOrderCallback(imageAdapter);
                    mRecyclerView.setAdapter(imageAdapter);
                    mRecyclerView.setFocusable(true);
                    initListener();
                    break;
            }
        }
    };
    private List<Image> images;
    private ScaleRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initThread();
    }

    private void initListener() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ==============");
                int childCount = mRecyclerView.getChildCount();
                Log.d(TAG, "onFocusChange: " + childCount);
                mRecyclerView.setFocusable(true);
                mRecyclerView.requestFocus();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void initThread() {
        ImageLoadThread imageLoadThread = new ImageLoadThread();
        imageLoadThread.start();
    }

    private class ImageLoadThread extends Thread {

        @Override
        public void run() {
            images = MediaResourceManager.getImagesFromMedia(ImageActivity.this);
            handler.sendEmptyMessage(0);
        }
    }


    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        mRecyclerView = (ScaleRecyclerView) findViewById(R.id.recycler_image);
        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("ImageActivity","hasfocus:"+hasFocus + ",");
                if(hasFocus){
                    if(mRecyclerView.getChildCount() >0){
                        Log.d("ImageActivity", "onFocusChange:-------- ");
                        mRecyclerView.getChildAt(0).requestFocus();

                    }
                }
            }
        });
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(R.string.image);
    }
}
