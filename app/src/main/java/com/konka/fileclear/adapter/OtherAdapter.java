package com.konka.fileclear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.Others;

import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.MyViewHolder> {

    private static final String TAG = "OtherAdapter";
    private Context mContext;
    private List<Others> mOtherses;

    public OtherAdapter(Context context, List<Others> otherses) {
        mContext = context;
        mOtherses = otherses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_audio, parent,
                false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String path = mOtherses.get(position).getPath();
        int lastIndex = path.lastIndexOf("/");
        String name = path.substring(lastIndex + 1);
        Log.d(TAG, "onBindViewHolder: " + name);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return mOtherses == null ? 0 : mOtherses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, smallIcon;
        private TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_audio);
            smallIcon = (ImageView) itemView.findViewById(R.id.iv_small_icon);
            smallIcon.setVisibility(View.GONE);
            name = (TextView) itemView.findViewById(R.id.audio_name);
        }
    }
}
