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

/**
 * Created by user001 on 2017-8-22.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private Context mContext;
    private List<Audio> mAudios;
    private int deletePosition = 0;
    private boolean isRefresh = true;

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
        setHolderView(holder, position);
        Log.d("AudioAdapter", "onBindViewHolder: " + mAudios.get(position).getName());
        holder.name.setText(mAudios.get(position).getName());
        holder.itemView.setFocusable(true);
        if (position == ((deletePosition - 1) < 0 ? 0 : (deletePosition - 1)) && isRefresh) {
            isRefresh = false;
            holder.itemView.requestFocus();

        }
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
        Audio audio = mAudios.get(position);
        File file = new File(audio.getPath());
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                mAudios.remove(position);
                deletePosition = position;
                isRefresh = true;
                notifyDataSetChanged();
            }
        }
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
