package com.konka.fileclear.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.Audio;

import java.io.File;
import java.util.List;

import static com.konka.fileclear.activity.MainActivity.TAG;

/**
 * Created by user001 on 2017-8-22.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private Context mContext;
    private List<Audio> mAudios;
    private int deletePosition = 0;

    public AudioAdapter(Context context, List<Audio>audios) {
        mContext = context;
        mAudios = audios;
    }

    @Override
    public AudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_audio, parent,
                false));
    }

    @Override
    public void onBindViewHolder(AudioAdapter.MyViewHolder holder, int position) {
        Log.d("AudioAdapter", "onBindViewHolder: " + mAudios.get(position).getName());
        holder.name.setText(mAudios.get(position).getName());
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
                    Audio audio = mAudios.get(position);
                    File file = new File(audio.getPath());
                    if (file.exists()) {
                        boolean delete = file.delete();
                        if (delete) {
                            mAudios.remove(position);
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
        return mAudios == null ? 0 : mAudios.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.audio_name);
        }
    }
}
