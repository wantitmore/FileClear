package com.konka.fileclear.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.konka.fileclear.R;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.entity.Audio;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    private static final String TAG = "AudioActivity";
    private static List<Audio> audios;
    private AudioHandler handler = new AudioHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        audios = new ArrayList<>();
        initThread();
    }

    private void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                audios = MediaResourceManager.getAudiosFromMedia(AudioActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private static class AudioHandler extends Handler {

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
                }
            }
        }
    }
}
