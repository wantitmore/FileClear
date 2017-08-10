package com.konka.fileclear;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.konka.fileclear.fragments.AppControllerFragment;
import com.konka.fileclear.fragments.ClearMasterFragment;
import com.konka.fileclear.fragments.SpaceControllerFragment;

public class MainActivity extends Activity implements View.OnFocusChangeListener{

    private int mCurrentId;

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
        mOneKeyClear = (RadioButton) findViewById(R.id.rb_one_key_clear);
        mSpaceController = (RadioButton) findViewById(R.id.rb_space_controller);
        mAppController = (RadioButton) findViewById(R.id.rb_app_controller);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.rb_one_key_clear :
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
