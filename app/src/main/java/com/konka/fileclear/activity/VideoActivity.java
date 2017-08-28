package com.konka.fileclear.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.VideoAdapter;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.entity.Video;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends Activity {

    private ScaleRecyclerView  mRecyclerView;
    private TextView mTitle;
    private static List<Video> mVideos;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(VideoActivity.this, 5));
                    VideoAdapter videoAdapter = new VideoAdapter(VideoActivity.this, mVideos);
                    mRecyclerView.setAdapter(videoAdapter);
                    break;
            }
        }
    };

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
        mVideos = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mVideos = MediaResourceManager.getVideosFromMedia(VideoActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        mRecyclerView = (ScaleRecyclerView ) findViewById(R.id.recycler_video);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText(getResources().getText(R.string.video));
    }
}
