package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.fileclear.R;
import com.konka.fileclear.activity.ClearMasterResultActivity;
import com.konka.fileclear.dao.StorageClear;
import com.konka.fileclear.utils.SdcardUtil;
import com.konka.fileclear.utils.TimeUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.konka.fileclear.R.id.tv_clean_last_time;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClearMasterFragment extends Fragment {


    private Button mOneKeyClear;
    private TextView mLastClearTime, mLastClearSpace, mTotalClearSpace;
    private TextView mStorageAvailable, mStorageTotal;
    private View mStorageRemain;
    private ImageView mStorageBall;
    private long mTotalClearSize;

    public ClearMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clear_master, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        mOneKeyClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // one key clear
                startActivity(new Intent(getActivity(), ClearMasterResultActivity.class));
            }
        });
    }

    private void initView(View view) {
        mOneKeyClear = (Button) view.findViewById(R.id.btn_optimizi);
        mLastClearTime = (TextView) view.findViewById(tv_clean_last_time);
        mLastClearSpace = (TextView) view.findViewById(R.id.tv_clean_last_size);
        mTotalClearSpace = (TextView) view.findViewById(R.id.tv_clean_total_size);
        mStorageAvailable = (TextView) view.findViewById(R.id.tv_storage_available_size);
        mStorageTotal = (TextView) view.findViewById(R.id.tv_storage_total_size);
        mStorageRemain = view.findViewById(R.id.storage_remain);
        mStorageBall = (ImageView)view.findViewById(R.id.iv_storage_ball);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // check total space and remain space
    }

    @Override
    public void onResume() {
        super.onResume();
        setStorageSituation();
    }

    private void setStorageSituation() {
        setStorageBall();
        setClearData();
    }

    private void setClearData() {
        Connector.getDatabase();
        List<StorageClear> storageClears = DataSupport.findAll(StorageClear.class);
        mTotalClearSize = 0;
        for (StorageClear storageClear : storageClears) {
            long lastClearSize = storageClear.getLastClearSize();
            mTotalClearSize += lastClearSize;
            Log.d(TAG, "setClearData: " + lastClearSize + ",  " + mTotalClearSize + ",  " + Formatter.formatFileSize(getActivity(), mTotalClearSize));
        }
        try {
            StorageClear lastClear = DataSupport.findLast(StorageClear.class);
            long lastClearTime = lastClear.getLastClearTime();
            long lastClearSize = lastClear.getLastClearSize();
            mLastClearSpace.setText(Formatter.formatFileSize(getActivity(), lastClearSize));
            mLastClearTime.setText(TimeUtil.convertTimeToFormat(getActivity(), lastClearTime));
            mTotalClearSpace.setText(Formatter.formatFileSize(getActivity(), mTotalClearSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStorageBall() {
        String sdTotalSize = SdcardUtil.getSDTotalSize(getActivity());
        String sdAvailableSize = SdcardUtil.getSDAvailableSize(getActivity());
        mStorageTotal.setText(sdTotalSize);
        mStorageAvailable.setText(sdAvailableSize);
        ViewGroup.LayoutParams remainLayoutParams = mStorageRemain.getLayoutParams();
        ViewGroup.LayoutParams ballLayoutParams = mStorageBall.getLayoutParams();
        double remainRatio = SdcardUtil.getAvailRatio(getActivity());
        remainLayoutParams.height = (int) (ballLayoutParams.height * remainRatio);
        mStorageRemain.setLayoutParams(remainLayoutParams);
    }

}
