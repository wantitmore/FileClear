package com.konka.fileclear.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.RemoteException;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.dao.StorageClear;
import com.konka.fileclear.utils.FileUtils;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static com.konka.fileclear.R.id.btn_done;

public class ClearMasterResultActivity extends Activity implements View.OnClickListener{

    private ImageView mFan;
    private TextView mScanPath, mClearing;
    private PackageManager pm;   //包管理
    long totalCache;
    private boolean isFirstClear = true;
    private RelativeLayout mScanningApp, mScanningLayout;
    private LinearLayout mClearResult;
    private TextView mCleanSize, mKillAppNum, mCacheTrash, mDeleteFile;;
    private long mUselessFile;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String mFileSize;
    private String retStrFormatNowDate;
    private long cleanSize;
    private long totalCleanSize;
    private Button mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_master_result);
        initView();
        initListener();
        verifyStoragePermissions(this);
        startScanning();
    }

    private void initListener() {
        mDone.setOnClickListener(this);
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

    private void startScanning() {
//        getTotalCleanSize();
        mUselessFile = FileUtils.deleteUselessFile();
        mFileSize = Formatter.formatFileSize(ClearMasterResultActivity.this, mUselessFile);
        scanCaches();
    }

    //扫描手机里面所有的应用程序的缓存
    private void scanCaches() {
        pm = getPackageManager();
        new Thread(){
            public void run() {
                Method getPackageSizeInfoMethod = null;
                //1.先获取PackageManager提供的所有方法
                Method[] methods = PackageManager.class.getMethods();
                for (Method method : methods) {
                    if("getPackageSizeInfo".equals(method.getName())) {
                        getPackageSizeInfoMethod = method;
                        break;
                    }
                }
                List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
                for (PackageInfo info : packageInfos) {
                    try {
                        Thread.sleep(100);
                        getPackageSizeInfoMethod.invoke(pm, info.packageName,
                                new MyDataObserver());
                    } catch (Exception e) {
                        Log.d(TAG, "run: error " + e.getCause().getMessage());
                        e.printStackTrace();
                    }
                }
                clearData();
                final String cache = Formatter.formatFileSize(ClearMasterResultActivity.this, totalCache);
                Log.d(TAG, "run: total cache is " + cache);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mScanningApp.setVisibility(GONE);
                        mClearing.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mClearing.setText(getResources().getText(R.string.clear_cache_useless_files));
                                mScanningLayout.setVisibility(GONE);
                                mClearResult.setVisibility(View.VISIBLE);
                                showCleanData(cache);
                            }
                        }, 1000);
                    }
                });
            };
        }.start();
    }

    private void showCleanData(String cache) {
        Context otherAppContext;
        try {
            cleanSize = mUselessFile + totalCache;
            otherAppContext = createPackageContext("com.android.systemui", Context.CONTEXT_IGNORE_SECURITY);
            int killedNum = otherAppContext.getSharedPreferences("kill_process", MODE_MULTI_PROCESS).getInt("kill_process_num", 0);
            mKillAppNum.setText(String.format(getResources().getString(R.string.running_apps), killedNum));
            mCacheTrash.setText(String.format(getResources().getString(R.string.cache_trash), cache));
            mDeleteFile.setText(String.format(getResources().getString(R.string.useless_file), mFileSize));
            mCleanSize.setText(String.format(getResources().getString(R.string.clean_size),

                    Formatter.formatFileSize(ClearMasterResultActivity.this, cleanSize)));
            saveData();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {

        StorageClear storageClear = new StorageClear();
        storageClear.setLastClearTime(System.currentTimeMillis());
        storageClear.setLastClearSize(cleanSize);
//        storageClear.setTotalClearSize(totalCleanSize + cleanSize);
        storageClear.save();
    }

    private void clearData() {
        CleanAllCache();
        killProcess();
    }

    private void killProcess() {
        Intent intent = new Intent();
        intent.setAction("com.android.systemui.recents.action.STATIC_CLEAN_RECENTS_APPS");
        intent.putExtra("file_clear", true);
        sendBroadcast(intent);
    }

    public long getTotalCleanSize() {
        Connector.getDatabase();
        List<StorageClear> storageClears = DataSupport.findAll(StorageClear.class);
        for (StorageClear storageClear : storageClears) {
            Log.d(TAG, "getTotalCleanSize: 111>" + storageClear.getLastClearSize());
            return totalCleanSize += storageClear.getLastClearSize();
        }
        Log.d(TAG, "getTotalCleanSize: 222___>");
        return totalCleanSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_done :
                startActivity(new Intent(ClearMasterResultActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private class MyDataObserver extends IPackageStatsObserver.Stub {

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            final long cache = pStats.cacheSize;
            final String packname = pStats.packageName;
            final ApplicationInfo appInfo;
            try {
                appInfo = pm.getApplicationInfo(packname, 0);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.d(TAG, "run: path is " + appInfo.loadLabel(pm));
                        mScanPath.setText(appInfo.loadLabel(pm));
                        if (isFirstClear) {
                            totalCache += cache;
                            isFirstClear = false;
                            Log.d(TAG, "run: first clear--");
                        } else if (cache > 12288) {
                            totalCache += cache;
                            Log.d(TAG, "run: numermous clear--");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private long getEnvironmentSize() {

        File localFile = Environment.getDataDirectory();

        long l1;

        if (localFile == null)

            l1 = 0L;

        while (true) {

            String str = localFile.getPath();

            StatFs localStatFs = new StatFs(str);

            long l2 = localStatFs.getBlockSize();

            l1 = localStatFs.getBlockCount() * l2;

            return l1;

        }
    }

    private class MyPackDataObserver extends IPackageDataObserver.Stub {

        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded)
                throws RemoteException {
            // save database
        }
    }

    private void CleanAllCache() {
        Method[] methods = PackageManager.class.getDeclaredMethods();
        for (Method method : methods) {
            if("freeStorageAndNotify".equals(method.getName())) {
                try {
                    method.invoke(pm, /*Integer.MAX_VALUE*/(getEnvironmentSize() - 1L), new MyPackDataObserver());
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.getCause().printStackTrace();
                    Log.e("tag", "CleanAllCache: " + e.getCause().getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("1tag", "CleanAllCache: " + e.getMessage());
                }

                return;
            }
        }
    }

        private void initView() {
            mDone = (Button) findViewById(btn_done);
            mFan = (ImageView) findViewById(R.id.iv_fan);
            mScanPath = (TextView) findViewById(R.id.tv_scan_path);
            mScanningApp = (RelativeLayout) findViewById(R.id.rl_scanning_app);
            mScanningLayout = (RelativeLayout) findViewById(R.id.rl_scanning);
            mClearResult = (LinearLayout) findViewById(R.id.ll_clear_result);
            mClearing = (TextView) findViewById(R.id.tv_clearing);
            mCleanSize = (TextView) findViewById(R.id.tv_clean_size);
            mKillAppNum = (TextView) findViewById(R.id.tv_running_apps);
            mCacheTrash = (TextView) findViewById(R.id.tv_cache_trash);
            mDeleteFile = (TextView) findViewById(R.id.tv_app_cache);
    }
}
