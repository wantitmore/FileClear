package com.konka.fileclear.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;

public class DeepClearActivity extends Activity {

    private ImageView mSearchAnim;
    private TextView mTitle, mDeleteHint;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_deep_clear);
        mSearchAnim = (ImageView) findViewById(R.id.iv_search_anim);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mDeleteHint = (TextView) findViewById(R.id.delete);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_deep);
        mTitle.setText(getResources().getText(R.string.deep_clear));
        viewToggle(false);
    }

    private void viewToggle(boolean isSearchEnd) {
        if (!isSearchEnd) {
            mSearchAnim.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mTitle.setVisibility(View.GONE);
            mDeleteHint.setVisibility(View.GONE);
        } else {
            mSearchAnim.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTitle.setVisibility(View.VISIBLE);
            mDeleteHint.setVisibility(View.VISIBLE);
        }
    }
}
