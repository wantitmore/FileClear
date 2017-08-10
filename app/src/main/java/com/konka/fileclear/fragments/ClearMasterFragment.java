package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.konka.fileclear.R;
import com.konka.fileclear.utils.SdcardUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClearMasterFragment extends Fragment {


    private Button mOneKeyClear;
    private TextView mLastClearTime, mLastClearSpace, mTotalClearSpace;

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

            }
        });
    }

    private void initView(View view) {
        mOneKeyClear = (Button) view.findViewById(R.id.btn_one_key_clear);
        mLastClearTime = (TextView) view.findViewById(R.id.tv_last_clear_time);
        mLastClearSpace = (TextView) view.findViewById(R.id.tv_last_clear_space);
        mTotalClearSpace = (TextView) view.findViewById(R.id.tv_total_clear_space);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // check total space and remain space
        String sdTotalSize = SdcardUtil.getSDTotalSize(getActivity());
        String sdAvailableSize = SdcardUtil.getSDAvailableSize(getActivity());
        Toast.makeText(getActivity(), sdTotalSize + "," + sdAvailableSize, Toast.LENGTH_SHORT).show();

//        initListener();
    }

}
