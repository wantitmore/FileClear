package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.konka.fileclear.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppControllerFragment extends Fragment {


    private Button mUninstallApp;

    public AppControllerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_controller, container, false);
        mUninstallApp = (Button) view.findViewById(R.id.app_controller);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    private void initListener() {
        final Intent intent = new Intent();
        String pkgName = "com.android.tv.settings";
        String className = "com.android.tv.settings.device.apps.AppsActivity";
        intent.setClassName(pkgName, className);
        mUninstallApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
