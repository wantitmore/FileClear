package com.konka.fileclear.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.Video;

import java.util.List;

/**
 * Created by user001 on 2017-8-24.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private static final String TAG = "VideoAdapter";
    private Context mContext;
    private List<Video> mVideos;

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
        Video video = mVideos.get(position);
        String path = video.getPath();
        String name = video.getName();
//        Bitmap thumbnail = MediaResourceManager.getVideoThumbnail(mContext, video.getId());
        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
//        Bitmap thumbnail = MediaResourceManager.getVideoThumbnail(path);
//        Bitmap thumbnail = getVideoThumbnail(path);
        holder.name.setText(name);
        holder.thumbnail.setImageBitmap(thumbnail);
        Log.d(TAG, "onBindViewHolder: path is " + path + ", name is " + name + ", size is " + android.R.attr.thumbnail + ", " + video.getId());
    }

    @Override
    public int getItemCount() {
        return mVideos == null  ? 0 : mVideos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail, smallIcon;
        private TextView name;
        MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById( R.id.iv_audio);
            smallIcon = (ImageView) itemView.findViewById( R.id.iv_small_icon);
            smallIcon.setImageResource(R.drawable.video1);
            name = (TextView) itemView.findViewById(R.id.audio_name);
        }
    }
}
