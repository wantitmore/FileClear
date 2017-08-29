package com.konka.fileclear.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.common.MediaResourceManager;
import com.konka.fileclear.utils.FocusUtil;
import com.konka.fileclear.view.ScaleRecyclerView;

import java.io.File;
import java.util.List;

public class ApplicationActivity extends Activity {

    private ScaleRecyclerView mRecyclerView;
    private List<PackageInfo> mCustomApps;
    private int uninstallPosition = 0;
    private static final String TAG = "ApplicationActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(ApplicationActivity.this, 5));
                    applicationAdapter = new ApplicationAdapter(ApplicationActivity.this, mCustomApps);
                    mRecyclerView.setAdapter(applicationAdapter);
                    mRecyclerView.setFocusable(true);
                    break;
            }
        }
    };
    private ApplicationAdapter applicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        handler.removeCallbacksAndMessages(null);
    }

    private void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCustomApps = MediaResourceManager.getCustomApps(ApplicationActivity.this);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    IntentFilter intentFilter;
    MyInstalledReceiver receiver;

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio);
        mRecyclerView = (ScaleRecyclerView) findViewById(R.id.recycler_audio);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(getResources().getText(R.string.apk));
        FocusUtil.focusListener(mRecyclerView);
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        receiver = new MyInstalledReceiver();
        registerReceiver(receiver, intentFilter);
    }


    public class MyInstalledReceiver extends BroadcastReceiver {
        public MyInstalledReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {   // uninstall
                String packageName = intent.getDataString();
                Log.d("MyInstalledReceiver", "卸载了 :" + packageName + ", uninstallPosition is " + uninstallPosition);
                mCustomApps.remove(uninstallPosition);
                applicationAdapter.notifyItemRemoved(uninstallPosition);
                applicationAdapter.notifyDataSetChanged();
            }
        }
    }


    class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {

        private Context mContext;
        private List<PackageInfo> mApkCommons;
        private static final String TAG = "ApplicationAdapter";
        static final int DELETE_REQUEST_CODE = 100;


        ApplicationAdapter(Context context, List<PackageInfo> apkCommons) {
            mContext = context;
            mApkCommons = apkCommons;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_audio, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            PackageManager pm = mContext.getPackageManager();
            holder.name.setText(mApkCommons.get(position).applicationInfo.loadLabel(pm).toString());
            holder.itemView.setFocusable(true);
            setHolderView(holder, position);
        }


        private void setHolderView(final MyViewHolder holder, final int position) {
            if (position == ((uninstallPosition - 1) < 0 ? 0 : (uninstallPosition - 1))) {
                holder.itemView.requestFocus();
                holder.itemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.animate(holder.itemView).scaleX(1.2f).scaleY(1.2f).translationZ(1).start();
                    }
                }, 300);
            }
            holder.itemView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                        //delete this item
                        PackageInfo info = mApkCommons.get(position);
                        String dataDir = info.applicationInfo.packageName;
                        File file = new File(dataDir);
                        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + dataDir));
                        uninstallPosition = position;
                        ((Activity) mContext).startActivityForResult(intent, DELETE_REQUEST_CODE);
                    }
                    return false;
                }
            });
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        ViewCompat.animate(v).scaleX(1.2f).scaleY(1.2f).translationZ(1).start();
                    } else {
                        ViewCompat.animate(v).scaleX(1f).scaleY(1f).translationZ(1).start();
                        ViewGroup parent = (ViewGroup) v.getParent();
                        if (parent != null) {
                            parent.requestLayout();
                            parent.invalidate();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mApkCommons == null ? 0 : mApkCommons.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView, apkIcon;
            private TextView name;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv_audio);
                apkIcon = (ImageView) itemView.findViewById(R.id.iv_small_icon);
                name = (TextView) itemView.findViewById(R.id.audio_name);
                apkIcon.setImageResource(R.drawable.apk1);
                imageView.setImageResource(R.drawable.apk_icon);
            }
        }
    }
}
