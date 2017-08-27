package com.konka.fileclear.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
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
                mContext).inflate(R.layout.item_image, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final ImageAdapter.MyViewHolder holder, int position) {
        String path = mImages.get(position).getPath();
        Bitmap bm = BitmapFactory.decodeFile(path);
        Bitmap bitmap = PictureLoader.decodeSampledBitmapFromResource(path, 165);
        Log.d(TAG, "onBindViewHolder: " + bitmap);
        holder.imageView.setImageBitmap(bitmap);
        holder.itemView.setFocusable(true);
//
        if (position == 0) {
            Log.d(TAG, "onBindViewHolder: ============");
            holder.itemView.requestFocus();
            ViewCompat.animate(holder.itemView).scaleX(1.2f).scaleY(1.2f).translationZ(1).start();
        }
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d(TAG, "onFocusChange: -------------------78");
                    ViewCompat.animate(v).scaleX(1.2f).scaleY(1.2f).translationZ(1).start();
//                    v.bringToFront();
//                    v.getParent().requestLayout();
                } else {
                    Log.d(TAG, "onFocusChange: -------------------77");
                    ViewCompat.animate(v).scaleX(1f).scaleY(1f).translationZ(1).start();
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parent.requestLayout();
                    parent.invalidate();
                }
                Log.d(TAG, "onFocusChange: ---------------");
            }
        });
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        MyViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    private class LoadImageThread extends Thread {
        @Override
        public void run() {
            super.run();

        }
    }
}
