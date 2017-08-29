package com.konka.fileclear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.entity.BigFile;

import java.io.File;
import java.util.List;

/**
 * Created by user001 on 2017-8-25.
 */

public class BigFileAdapter extends RecyclerView.Adapter<BigFileAdapter.MyViewHolder> {

    private Context mContext;
    private List<BigFile>mBigFiles;
    private static final String TAG = "BigFileAdapter";
    private int deletePosition = 0;

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
        holder.itemView.setFocusable(true);
        setHolderView(holder, position);
    }

    private void setHolderView(final MyViewHolder holder, final int position) {
        if (position == ((deletePosition - 1) < 0 ? 0 : (deletePosition - 1))) {
            holder.itemView.requestFocus();
        }

        holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //delete this item
                    BigFile bigFile = mBigFiles.get(position);
                    File file = new File(bigFile.getPath());
                    if (file.exists()) {
                        boolean delete = file.delete();
                        if (delete) {
                            mBigFiles.remove(position);
                            deletePosition = position;
                            notifyDataSetChanged();
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBigFiles == null ? 0 : mBigFiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, size;
        private MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            size = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}
