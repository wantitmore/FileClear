package com.konka.fileclear.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.konka.fileclear.R;
import com.konka.fileclear.fragments.AppControllerFragment;
import com.konka.fileclear.fragments.ClearMasterFragment;
import com.konka.fileclear.fragments.SpaceControllerFragment;

import static com.konka.fileclear.R.id.rb_one_key_clear;

public class MainActivity extends Activity implements View.OnFocusChangeListener{

    private RadioGroup mClearGroup;
    private AppControllerFragment mAppControllerFragment;
    private SpaceControllerFragment mSpaceControllerFragment;
    private ClearMasterFragment mOneKeyClearFragment;

    public static final String TAG = "MainActivity";
    private RadioButton mOneKeyClear, mSpaceController, mAppController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initFragment();

        initListener();
    }

    private void initListener() {
        mOneKeyClear.setOnFocusChangeListener(this);
        mSpaceController.setOnFocusChangeListener(this);
        mAppController.setOnFocusChangeListener(this);
    }

    private void switchFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl_main_content, fragment).show(fragment).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mOneKeyClear = (RadioButton) findViewById(rb_one_key_clear);
        mSpaceController = (RadioButton) findViewById(R.id.rb_space_controller);
        mAppController = (RadioButton) findViewById(R.id.rb_app_controller);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int measuredHeight = mClearGroup.getMeasuredHeight();
        Log.d(TAG, "onAttachedToWindow: " + measuredHeight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mOneKeyClearFragment.isVisible() && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mClearGroup.check(R.id.rb_one_key_clear);
            mOneKeyClear.setNextFocusDownId(R.id.rg_main);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: onFocusChange:4");
        super.onDestroy();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case rb_one_key_clear :
                    switchFragment(mOneKeyClearFragment);
                    break;
                case R.id.rb_space_controller :
                    switchFragment(mSpaceControllerFragment);
                    break;
                case R.id.rb_app_controller :
                    switchFragment(mAppControllerFragment);
                    break;
            }
        }
    }
}
