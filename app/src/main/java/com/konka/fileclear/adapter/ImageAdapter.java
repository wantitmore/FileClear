package com.konka.fileclear.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.konka.fileclear.R;
import com.konka.fileclear.common.PictureLoader;
import com.konka.fileclear.entity.Image;

import java.util.List;

/**
 * Created by user001 on 2017-8-23.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> implements AbsListView.OnScrollListener {

    private static final String TAG = "ImageAdapter";
    private Context mContext;
    private List<Image> mImages;
    private LoadImageThread mThread;
    private PictureLoader mImageLoader = PictureLoader.getInstance();

    public ImageAdapter(Context context, List<Image> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_audio, parent,
                false));
    }

    @Override
    public void onBindViewHolder(ImageAdapter.MyViewHolder holder, int position) {
        String path = mImages.get(position).getPath();
        Bitmap bm = BitmapFactory.decodeFile(path);
        Bitmap bitmap = PictureLoader.decodeSampledBitmapFromResource(path, 165);
        Log.d(TAG, "onBindViewHolder: " + bitmap);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mImages.size());
        return mImages == null ? 0 : mImages.size();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            mThread = new LoadImageThread();
            mThread.start();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

   /* class LoadImageThread extends Thread {

        @Override
        public void run() {
            int end = mStart + mCount;
            for (int i = mStart; i < end; i++) {
                String path = mImages.get(i);
                Bitmap bitmap = mImageLoader.getBitmapFromMemoryCache(path);
                if (bitmap == null) {
                    bitmap = PictureLoader.decodeSampledBitmapFromResource(path, 120);
                    mImageLoader.addBitmapToMemoryCache(path, bitmap);
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable)bitmap;
                Drawable drawable = (Drawable)bitmapDrawable;
                Message msg = new Message();
                msg.what = 0;
                msg.obj = new MyViewHolder(bitmap);
                mHandler.sendMessage(msg);
            }
        }
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_audio);
        }
    }

    private class LoadImageThread extends Thread {
        @Override
        public void run() {
            super.run();

        }
    }
}
