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
import com.konka.fileclear.adapter.OtherAdapter;
import com.konka.fileclear.entity.Others;
import com.konka.fileclear.utils.FocusUtil;
import com.konka.fileclear.utils.SearchUtil;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OthersActivity extends Activity {

    private List<Others> mOtherses;
    private ScaleRecyclerView  mRecyclerView;
    String[] others = new String[]{".zip", ".rar", ".tar", ".gz", "tgz", "txt", ".doc", ".docx",
            ".xls", ".xlsx", ".ppt", "pptx", ".xml", ".html", ".htm", ".apk"};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(OthersActivity.this, 5));
                    OtherAdapter videoAdapter = new OtherAdapter(OthersActivity.this, mOtherses);
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
        new Thread(new Runnable() {

            @Override
            public void run() {
                mOtherses = new ArrayList<>();
                ArrayList<String> supportFileList = SearchUtil.getSupportFileList(OthersActivity.this, others);
                for (String supportFile : supportFileList) {
                    Others other = new Others();
                    other.setPath(supportFile);
                    mOtherses.add(other);
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio);
        mRecyclerView = (ScaleRecyclerView ) findViewById(R.id.recycler_audio);
        FocusUtil.focusListener(mRecyclerView);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(getResources().getText(R.string.others));
    }
}
