package com.konka.fileclear.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.konka.fileclear.R;
import com.konka.fileclear.utils.FileUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ClearMasterResultActivity extends Activity {

    private ImageView mFan;
    private TextView mScanPath;
    private PackageManager pm;   //包管理
    long totalCache;
    private boolean isFirstClear = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_master_result);
        initView();
        startScanning();
    }

    private void startScanning() {
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
                        e.printStackTrace();
                    }
                }
                String s = Formatter.formatFileSize(ClearMasterResultActivity.this, totalCache);
                Log.d(TAG, "run: total cache is " + s);
//                CleanAllCache();
                FileUtils.deleteUselessFile();
                killProcess();
            };
        }.start();
    }

    private void killProcess() {
        Intent intent = new Intent();
        intent.setAction("com.android.systemui.recents.action.STATIC_CLEAN_RECENTS_APPS");
        intent.putExtra("file_clear", true);
        sendBroadcast(intent);
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
                        } else if (cache > 12288) {
                            totalCache += cache;
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "缓存删除成功", Toast.LENGTH_SHORT).show();
//                    ll_container.removeAllViews();
//                    scan_state.setText("缓存清理成功");
                    Toast.makeText(ClearMasterResultActivity.this, "total is " + Formatter.formatFileSize(ClearMasterResultActivity.this, totalCache), Toast.LENGTH_SHORT).show();
                }
            });
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
        mFan = (ImageView) findViewById(R.id.iv_fan);
        mScanPath = (TextView) findViewById(R.id.tv_scan_path);
    }
}
