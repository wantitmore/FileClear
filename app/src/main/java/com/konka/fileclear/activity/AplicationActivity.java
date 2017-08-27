package com.konka.fileclear.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.AplicationAdapter;
import com.konka.fileclear.common.MediaResourceManager;

import java.util.List;

public class AplicationActivity extends Activity {

    private RecyclerView mRecyclerView;
    private List<PackageInfo> customApps;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(AplicationActivity.this, 5));
                    AplicationAdapter aplicationAdapter = new AplicationAdapter(AplicationActivity.this, customApps);
                    mRecyclerView.setAdapter(aplicationAdapter);
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

    private void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                customApps = MediaResourceManager.getCustomApps(AplicationActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_audio);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(getResources().getText(R.string.apk));
        mRecyclerView.setFocusable(true);
        final boolean b = mRecyclerView.requestFocus();
        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("ImageActivity","hasfocus:"+hasFocus + ", " + b);
                if(hasFocus){
                    if(mRecyclerView.getChildCount()>0){
                        mRecyclerView.getChildAt(0).requestFocus();
                    }
                }
            }
        });
    }
}
