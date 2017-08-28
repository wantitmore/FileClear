package com.konka.fileclear.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.ApplicationAdapter;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.utils.FocusUtil;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.util.List;

public class ApplicationActivity extends Activity {

    private ScaleRecyclerView  mRecyclerView;
    private List<PackageInfo> mCustomApps;
    private static final String TAG = "ApplicationActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(ApplicationActivity.this, 5));
                    applicationAdapter = new ApplicationAdapter(ApplicationActivity.this, mCustomApps);
                    mRecyclerView.setAdapter(applicationAdapter);
                    mRecyclerView.setFocusable(true);
                    break;
            }
        }
    };
    private ApplicationAdapter applicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initThread();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApplicationAdapter.DELETE_REQUEST_CODE) {
//            Log.d(TAG, "onActivityResult: delete success ,position is " + position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: size2 " + mCustomApps.size());
                    mCustomApps = MediaResourceManager.getCustomApps(ApplicationActivity.this);
                    Log.d(TAG, "run: size1 " + mCustomApps.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            applicationAdapter = new ApplicationAdapter(ApplicationActivity.this, mCustomApps);
                            mRecyclerView.setAdapter(applicationAdapter);
                        }
                    });
                }
            }).start();
        }
    }

    private void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCustomApps = MediaResourceManager.getCustomApps(ApplicationActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio);
        mRecyclerView = (ScaleRecyclerView) findViewById(R.id.recycler_audio);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(getResources().getText(R.string.apk));
        FocusUtil.focusListener(mRecyclerView);
    }

}
