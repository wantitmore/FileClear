package com.konka.fileclear.adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private boolean isRefresh = true;

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
        setHolderView(holder, position);
        String path = mOtherses.get(position).getPath();
        int lastIndex = path.lastIndexOf("/");
        String name = path.substring(lastIndex + 1);
        holder.name.setText(name);
        if (position == ((deletePosition - 1) < 0 ? 0 : (deletePosition - 1))&& isRefresh) {
            isRefresh = false;
            holder.itemView.requestFocus();
            Log.d(TAG, "onBindViewHolder: ------------");
        }
        holder.itemView.setFocusable(true);
    }

    private void setHolderView(final MyViewHolder holder, final int position) {

        holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //delete this item
                    deleteItem(position);
                    return true;
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
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
    }

    private void deleteItem(int position) {
        Others others = mOtherses.get(position);
        File file = new File(others.getPath());
        Log.d(TAG, "deleteItem: click-----" + file.getAbsolutePath());
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                mOtherses.remove(position);
                deletePosition = position;
                isRefresh = true;
                notifyDataSetChanged();
                mContext.getContentResolver().delete(
                        MediaStore.Files.getContentUri("external"),
                        MediaStore.Audio.Media.DATA+ " = '" + file.getAbsolutePath() + "'", null);;
                Log.d(TAG, "deleteItem: ------------");
                Toast.makeText(mContext, mContext.getText(R.string.delete_fail), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, mContext.getText(R.string.delete_fail), Toast.LENGTH_SHORT).show();
            }
        }
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
            name = (TextView) itemView.findViewById(R.id.audio_name);
            smallIcon.setImageResource(R.drawable.others1);
            imageView.setImageResource(R.drawable.others_icon);
        }
    }
}
