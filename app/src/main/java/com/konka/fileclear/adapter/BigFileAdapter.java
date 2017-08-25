package com.konka.fileclear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.BigFile;

import java.util.List;

/**
 * Created by user001 on 2017-8-25.
 */

public class BigFileAdapter extends RecyclerView.Adapter<BigFileAdapter.MyViewHolder> {

    private Context mContext;
    private List<BigFile>mBigFiles;

    public BigFileAdapter(Context context, List<BigFile> bigFiles) {
        mContext = context;
        mBigFiles = bigFiles;
    }

    @Override
    public BigFileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_big_file, parent, false));
    }

    @Override
    public void onBindViewHolder(BigFileAdapter.MyViewHolder holder, int position) {
        BigFile bigFile = mBigFiles.get(position);
        String name = bigFile.getName();
        String size = bigFile.getSize();
        holder.name.setText(name);
        holder.size.setText(size);
    }

    @Override
    public int getItemCount() {
        return mBigFiles == null ? 0 : mBigFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, size;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            size = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}
