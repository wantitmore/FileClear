package com.konka.fileclear;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
    private ClearMasterFragment mOneKeyClearFragment;

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
                if (mCurrentId != checkedId) {
                    Fragment fragment = null;
                    mCurrentId = checkedId;
                    switch (checkedId) {
                        case R.id.rb_one_key_clear :
                            fragment = mOneKeyClearFragment;
                            break;
                        case R.id.rb_space_controller :
                            fragment = mSpaceControllerFragment;
                            break;
                        case R.id.rb_app_controller :
                            fragment = mAppControllerFragment;
                            break;
                    }
                    switchFragment(fragment);
                }
            }
        });
        mClearGroup.check(R.id.rb_one_key_clear);
        mCurrentId = R.id.rb_one_key_clear;
        switchFragment(mAppControllerFragment);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main_content, fragment).show(fragment).commitAllowingStateLoss();
    }

    private void initFragment() {
        mOneKeyClearFragment = new ClearMasterFragment();
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
