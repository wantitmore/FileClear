package com.konka.fileclear.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;

import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class AplicationAdapter extends RecyclerView.Adapter<AplicationAdapter.MyViewHolder> {

    private Context mContext;
    private List<PackageInfo> mApkCommons;

    public AplicationAdapter(Context context, List<PackageInfo> apkCommons) {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PackageManager pm = mContext.getPackageManager();
        holder.name.setText(mApkCommons.get(position).applicationInfo.loadLabel(pm).toString());
//        Drawable drawable = mApkCommons.get(position).applicationInfo.loadIcon(pm);
//        holder.imageView.setImageDrawable(drawable);
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
