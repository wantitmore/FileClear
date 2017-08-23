package com.konka.fileclear.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.ImageAdapter;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.entity.Image;

import java.util.List;

public class ImageActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(ImageActivity.this, 5));
                    ImageAdapter imageAdapter = new ImageAdapter(ImageActivity.this, images);
                    mRecyclerView.setAdapter(imageAdapter);
                    break;
            }
        }
    };
    private List<Image> images;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initThread();
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
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_image);
    }
}
