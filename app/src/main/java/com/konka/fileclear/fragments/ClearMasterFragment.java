package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.konka.fileclear.R;
import com.konka.fileclear.utils.SdcardUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClearMasterFragment extends Fragment {


    public ClearMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clear_master, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // check total space and remain space
        String sdTotalSize = SdcardUtil.getSDTotalSize(getActivity());
        String sdAvailableSize = SdcardUtil.getSDAvailableSize(getActivity());
        Toast.makeText(getActivity(), sdTotalSize + "," + sdAvailableSize, Toast.LENGTH_SHORT).show();

    }

}
