package com.konka.fileclear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.Audio;

import java.util.List;

/**
 * Created by user001 on 2017-8-22.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private Context mContext;
    private List<Audio> audios;

    public AudioAdapter(Context context, List<Audio>audios) {
        mContext = context;
        this.audios = audios;
    }

    @Override
    public AudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_audio, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(AudioAdapter.MyViewHolder holder, int position) {
        Log.d("3345", "onBindViewHolder: " + audios.get(position).getName());
        holder.name.setText(audios.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return audios == null ? 0 : audios.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.audio_name);
        }
    }
}
