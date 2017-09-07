package com.konka.fileclear.adapter;

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.konka.fileclear.R;
import com.konka.fileclear.entity.Video;

import java.io.File;
import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private static final String TAG = "VideoAdapter";
    private Context mContext;
    private List<Video> mVideos;
    private int deletePosition = 0;
    private boolean isRefresh = true;

    public VideoAdapter(Context context, List<Video> videos) {
        mContext = context;
        mVideos = videos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_audio, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        setHolderView(holder, position);
        Video video = mVideos.get(position);
        String path = video.getPath();
        String name = video.getName();
        holder.name.setText(name);
        Glide.with(mContext).load(Uri.fromFile(new File(path))).placeholder(R.drawable.video_default).into(holder.thumbnail);
        if (position == ((deletePosition - 1) < 0 ? 0 : (deletePosition - 1)) && isRefresh) {
            Log.d(TAG, "onBindViewHolder: -----------------34");
            isRefresh = false;
            holder.itemView.requestFocus();
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
                    Log.d(TAG, "onFocusChange: focus");
                    ViewCompat.animate(v).scaleX(1.2f).scaleY(1.2f).translationZ(10).start();
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
        Video image = mVideos.get(position);
        File file = new File(image.getPath());
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                mVideos.remove(position);
                deletePosition = position;
                isRefresh = true;
                notifyDataSetChanged();
            } else {
                Toast.makeText(mContext, mContext.getText(R.string.delete_fail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mVideos == null ? 0 : mVideos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail, smallIcon;
        private TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.iv_audio);
            smallIcon = (ImageView) itemView.findViewById(R.id.iv_small_icon);
            smallIcon.setImageResource(R.drawable.video1);
            name = (TextView) itemView.findViewById(R.id.audio_name);
        }
    }
}
