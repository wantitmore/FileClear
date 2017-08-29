package com.konka.fileclear.adapter;

import android.content.Context;
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
import com.konka.fileclear.entity.Others;

import java.io.File;
import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.MyViewHolder> {

    private static final String TAG = "OtherAdapter";
    private Context mContext;
    private List<Others> mOtherses;
    private int deletePosition = 0;

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
        holder.name.setText(name);
        holder.itemView.setFocusable(true);
        setHolderView(holder, position);
    }

    private void setHolderView(final MyViewHolder holder, final int position) {
        if (position == ((deletePosition - 1) < 0 ? 0 : (deletePosition - 1))) {
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
                    Others image = mOtherses.get(position);
                    File file = new File(image.getPath());
                    if (file.exists()) {
                        boolean delete = file.delete();
                        if (delete) {
                            mOtherses.remove(position);
                            deletePosition = position;
                            notifyDataSetChanged();
                        }
                    }
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
                    Log.d(TAG, "onFocusChange: image unfocus");
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
