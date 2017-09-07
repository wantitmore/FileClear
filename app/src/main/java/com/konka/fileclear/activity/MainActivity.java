package com.konka.fileclear.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.fragments.AppControllerFragment;
import com.konka.fileclear.fragments.ClearMasterFragment;
import com.konka.fileclear.fragments.SpaceControllerFragment;

public class MainActivity extends Activity implements View.OnFocusChangeListener, View.OnClickListener{

    private LinearLayout mClearGroup;
    private AppControllerFragment mAppControllerFragment;
    private SpaceControllerFragment mSpaceControllerFragment;
    private ClearMasterFragment mOneKeyClearFragment;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String TAG = "MainActivity";
    private TextView mOneKeyClear, mSpaceController, mAppController;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ImageView lineAppManager;
    private ImageView lineClearMaster;
    private ImageView lineStorageManager;
    private static final int CLEAR_MASTER = 0;
    private static final int STORAGE_MANAGER = 1;
    private static final int APP_MMANAGER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initFragment();

        verifyStoragePermissions(this);

        initListener();
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void initListener() {
        Log.d(TAG, "initListener: -----------------");
        mOneKeyClear.setOnFocusChangeListener(this);
        mSpaceController.setOnFocusChangeListener(this);
        mAppController.setOnFocusChangeListener(this);
        mOneKeyClear.setOnClickListener(this);
        mSpaceController.setOnClickListener(this);
        mAppController.setOnClickListener(this);
        switchFragment(mOneKeyClearFragment);
        setLineVisable(CLEAR_MASTER);
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
        mClearGroup = (LinearLayout) findViewById(R.id.rg_main);
        mOneKeyClear = (TextView) findViewById(R.id.rb_one_key_clear);
        mSpaceController = (TextView) findViewById(R.id.rb_space_controller);
        mAppController = (TextView) findViewById(R.id.rb_app_controller);
        lineAppManager = (ImageView) findViewById(R.id.line_app_manager);
        lineClearMaster = (ImageView) findViewById(R.id.line_clear_master);
        lineStorageManager = (ImageView) findViewById(R.id.line_storage_manager);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.enterandentrance;
        getWindow().setAttributes(params);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int measuredHeight = mClearGroup.getMeasuredHeight();
        Log.d(TAG, "onAttachedToWindow: " + measuredHeight);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.rb_one_key_clear :
                    switchFragment(mOneKeyClearFragment);
                    setLineVisable(CLEAR_MASTER);
                    break;
                case R.id.rb_space_controller :
                    switchFragment(mSpaceControllerFragment);
                    setLineVisable(STORAGE_MANAGER);
                    break;
                case R.id.rb_app_controller :
                    switchFragment(mAppControllerFragment);
                    setLineVisable(APP_MMANAGER);
                    break;
            }
        }
    }

    private void setLineVisable(int num) {
        lineAppManager.setVisibility(View.INVISIBLE);
        lineClearMaster.setVisibility(View.INVISIBLE);
        lineStorageManager.setVisibility(View.INVISIBLE);
        mOneKeyClear.setTextColor(getResources().getColor(R.color.color_8fbeff));
        mSpaceController.setTextColor(getResources().getColor(R.color.color_8fbeff));
        mAppController.setTextColor(getResources().getColor(R.color.color_8fbeff));
        switch (num) {
            case CLEAR_MASTER:
                lineClearMaster.setVisibility(View.VISIBLE);
                mOneKeyClear.setTextColor(getResources().getColor(R.color.color_white));
                break;
            case STORAGE_MANAGER:
                lineStorageManager.setVisibility(View.VISIBLE);
                mSpaceController.setTextColor(getResources().getColor(R.color.color_white));
                break;
            case APP_MMANAGER:
                lineAppManager.setVisibility(View.VISIBLE);
                mAppController.setTextColor(getResources().getColor(R.color.color_white));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: " + v.toString());
        switch (v.getId()) {
            case R.id.rb_one_key_clear :
                Log.d(TAG, "onClick1: " + v.toString());
                switchFragment(mOneKeyClearFragment);
                setLineVisable(CLEAR_MASTER);
                break;
            case R.id.rb_space_controller :
                Log.d(TAG, "onClick2: " + v.toString());
                switchFragment(mSpaceControllerFragment);
                setLineVisable(STORAGE_MANAGER);
                break;
            case R.id.rb_app_controller :
                Log.d(TAG, "onClick3: " + v.toString());
                switchFragment(mAppControllerFragment);
                setLineVisable(APP_MMANAGER);
                break;
        }
    }
}
