package com.konka.fileclear.activity;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.AudioAdapter;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.entity.Audio;
import com.konka.fileclear.utils.FocusUtil;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends Activity {

    private static final String TAG = "AudioActivity";
    private static List<Audio> audios;
    private AudioHandler handler = new AudioHandler(this);
    private ScaleRecyclerView mRecyclerView;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        MediaScannerConnection.scanFile(this, new String[] { Environment
                .getExternalStorageDirectory().getAbsolutePath() }, null, null);
        initThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio);
        mRecyclerView = (ScaleRecyclerView ) findViewById(R.id.recycler_audio);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText(getResources().getText(R.string.music));
        FocusUtil.focusListener(mRecyclerView);
    }

    private void initThread() {
        audios = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                audios = MediaResourceManager.getAudiosFromMedia(AudioActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private class AudioHandler extends Handler {

        private final WeakReference<AudioActivity> mActivity;

        AudioHandler(AudioActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AudioActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0 :
                        Log.d(TAG, "handleMessage: " + audios.size());
                        //show audio RecyclerView
                        mRecyclerView .setLayoutManager(new GridLayoutManager(AudioActivity.this, 5));
                        mRecyclerView.setAdapter(new AudioAdapter(AudioActivity.this, audios));
                }
            }
        }
    }

}
