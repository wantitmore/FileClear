package com.konka.fileclear.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konka.fileclear.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceControllerFragment extends Fragment {


    public SpaceControllerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_space_controller, container, false);
    }

}
