package com.konka.fileclear.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;

import java.io.File;
import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {

    private Context mContext;
    private List<PackageInfo> mApkCommons;
    private static final String TAG ="ApplicationAdapter";
    public static final int DELETE_REQUEST_CODE = 100;
    private int uninstallPosition = 0;


    public ApplicationAdapter(Context context, List<PackageInfo> apkCommons) {
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
        Log.d(TAG, "onBindViewHolder: notify test");
        setHolderView(holder, position);
    }

    public class MyInstalledReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
         if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {   // uninstall
                String packageName = intent.getDataString();
                Log.d("homer", "卸载了 :" + packageName);
                mApkCommons.remove(uninstallPosition);
                notifyItemRemoved(uninstallPosition);
            }
        }
    }

    private void setHolderView(final MyViewHolder holder, final int position) {
        if (position == 0) {
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
                Log.d(TAG, "onKey: --------keycode is " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //delete this item
                    PackageInfo info = mApkCommons.get(position);
                    String dataDir = info.applicationInfo.packageName;
                    File file = new File(dataDir);
                    Log.d(TAG, "onKey: ----file is " + dataDir);
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
                Log.d(TAG, "onFocusChange: -------------------------zack");
                if (hasFocus) {
                    Log.d(TAG, "onFocusChange: focus is ");
                    ViewCompat.animate(v).scaleX(1.2f).scaleY(1.2f).translationZ(1).start();
                } else {
                    Log.d(TAG, "onFocusChange: ---zack unfocus");
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
