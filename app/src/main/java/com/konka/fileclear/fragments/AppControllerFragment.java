package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konka.fileclear.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppControllerFragment extends Fragment {


    public AppControllerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_controller, container, false);
    }

}
