package com.konka.fileclear.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.konka.fileclear.R;
import com.konka.fileclear.common.PictureLoader;
import com.konka.fileclear.entity.Image;

import java.io.File;
import java.util.List;

/**
 * Created by user001 on 2017-8-23.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> implements AbsListView.OnScrollListener{

    private static final String TAG = "ImageAdapter";
    private Context mContext;
    private List<Image> mImages;
    private LoadImageThread mThread;
    private int deletePosition = 0;
    private int mStart = 0;
    private int mCount = 0;
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
    public void onBindViewHolder(final ImageAdapter.MyViewHolder holder, final int position) {
        final String path = mImages.get(position).getPath();
        Bitmap bitmap = PictureLoader.decodeSampledBitmapFromResource(path, 165);
        holder.imageView.setImageBitmap(bitmap);
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
                    Image image = mImages.get(position);
                    File file = new File(image.getPath());
                    if (file.exists()) {
                        boolean delete = file.delete();
                        if (delete) {
                            mImages.remove(position);
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
            /*String path = mImages.get(i);
            Bitmap bitmap = mImageLoader.getBitmapFromMemoryCache(path);
            if (bitmap == null) {
                bitmap = CMImageLoader.decodeSampledBitmapFromResource(path, 120);
                mImageLoader.addBitmapToMemoryCache(path, bitmap);
            }

            Message msg = new Message();
            msg.what = MSG_UPDATE;
            msg.obj = new Holder(bitmap, i);
            mHandler.sendMessage(msg);*/
        }
    }
}
