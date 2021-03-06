package com.konka.fileclear.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.adapter.BigFileAdapter;
import com.konka.fileclear.entity.BigFile;
import com.konka.fileclear.utils.SearchUtil;
import com.konka.fileclear.utils.search.SatelliteAnimator;

import java.util.List;

public class DeepClearActivity extends Activity {

    private static final String TAG = "DeepClearActivity";
    private ImageView mSearchAnim;
    private TextView mTitle, mDeleteHint;
    private RecyclerView mRecyclerView;
    private List<BigFile> mBigFiles;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    viewToggle(true);
                    long totalSize = 0;
                    for (BigFile bigFile : mBigFiles) {
                        totalSize += bigFile.getRealSize();
                    }
                    String size = Formatter.formatFileSize(DeepClearActivity.this, totalSize);
                    mTitle.setText(getResources().getString(R.string.deep_clear, (mBigFiles == null ? 0 : mBigFiles.size()), size));
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(DeepClearActivity.this));
                    BigFileAdapter bigFileAdapter = new BigFileAdapter(DeepClearActivity.this, mBigFiles);
                    mRecyclerView.setAdapter(bigFileAdapter);
                    break;
            }
        }
    };
    private TextView mScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initView();
        startDeepSearch();
//        new Thread(run2).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void startDeepSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchUtil searchUtil = new SearchUtil();
                mBigFiles = searchUtil.getBigFileList(DeepClearActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath());
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_deep_clear);
        mSearchAnim = (ImageView) findViewById(R.id.iv_search_anim);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mDeleteHint = (TextView) findViewById(R.id.delete);
        mScanning = (TextView) findViewById(R.id.tv_scanning);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_deep);
        mTitle.setText(getResources().getText(R.string.deep_clear));
        viewToggle(false);
        startAnimation();
    }


    public void startAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.d(TAG, "startAnimation: " + screenWidth + ", " + screenHeight);
        Rect rect = new Rect(screenWidth / 2 - 134, screenHeight /2 - 134, screenWidth / 2 + 134, screenHeight /2 + 134);
        SatelliteAnimator satelliteAnimator = new SatelliteAnimator(rect);
        satelliteAnimator.setAutoScaleEnable(true);
        satelliteAnimator.setRepeatMode(ValueAnimator.RESTART);
        satelliteAnimator.setRepeatCount(500);
        satelliteAnimator.startSatelliteAnimation(mSearchAnim, 0, 360, 6000);
    }

    private void viewToggle(boolean isSearchEnd) {
        if (!isSearchEnd) {
            mSearchAnim.setVisibility(View.VISIBLE);
            mScanning.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mTitle.setVisibility(View.GONE);
            mDeleteHint.setVisibility(View.GONE);
        } else {
            mSearchAnim.setVisibility(View.GONE);
            mScanning.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTitle.setVisibility(View.VISIBLE);
            mDeleteHint.setVisibility(View.VISIBLE);
        }
    }
}
