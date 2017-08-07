package com.konka.fileclear;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.konka.fileclear.fragments.AppControllerFragment;
import com.konka.fileclear.fragments.ClearMasterFragment;
import com.konka.fileclear.fragments.SpaceControllerFragment;

public class MainActivity extends Activity {

    private int mCurrentId;

    private RadioGroup mClearGroup;
    private AppControllerFragment mAppControllerFragment;
    private SpaceControllerFragment mSpaceControllerFragment;
    private ClearMasterFragment mClearMasterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initFragment();

        initListener();
    }

    private void initListener() {
        mClearGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            }
        });
        mClearGroup.check(R.id.rb_one_key_clear);
        mCurrentId = R.id.rb_one_key_clear;
        switchFragment(mCurrentId);
    }

    private void switchFragment(int mCurrentId) {
        switch (mCurrentId) {
            case R.id.rb_one_key_clear :

                break;
        }
    }

    private void initFragment() {
        mClearMasterFragment = new ClearMasterFragment();
        mSpaceControllerFragment = new SpaceControllerFragment();
        mAppControllerFragment = new AppControllerFragment();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mClearGroup = (RadioGroup) findViewById(R.id.rg_main);
    }
}
