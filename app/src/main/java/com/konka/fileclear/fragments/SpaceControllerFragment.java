package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.konka.fileclear.R;

import static com.konka.fileclear.R.id.ib_apk;
import static com.konka.fileclear.R.id.ib_deep_clear;
import static com.konka.fileclear.R.id.ib_image;
import static com.konka.fileclear.R.id.ib_music;
import static com.konka.fileclear.R.id.ib_others;
import static com.konka.fileclear.R.id.ib_video;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceControllerFragment extends Fragment implements View.OnFocusChangeListener{

    private static final String TAG = "SpaceControllerFragment";
    private ImageButton mImages, mDeepClear, mVideo, mMusic, mApk, mOthers;

    public SpaceControllerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_space_controller, container, false);
        mDeepClear = (ImageButton) view.findViewById(ib_deep_clear);
        mImages = (ImageButton) view.findViewById(R.id.ib_image);
        mVideo = (ImageButton) view.findViewById(R.id.ib_video);
        mMusic = (ImageButton) view.findViewById(R.id.ib_music);
        mApk = (ImageButton) view.findViewById(R.id.ib_apk);
        mOthers = (ImageButton) view.findViewById(R.id.ib_others);
        initListener();
        return view;
    }

    private void initListener() {
        mDeepClear.setOnFocusChangeListener(this);
        mImages.setOnFocusChangeListener(this);
        mVideo.setOnFocusChangeListener(this);
        mMusic.setOnFocusChangeListener(this);
        mApk.setOnFocusChangeListener(this);
        mOthers.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case ib_deep_clear :
                Log.d(TAG, "onFocusChange: clear");
                if (hasFocus) {
                    
                } else {

                }
                break;
            case ib_image :
                Log.d(TAG, "onFocusChange: image");
                if (hasFocus) {

                } else {

                }
                break;
            case ib_video :
                Log.d(TAG, "onFocusChange: video");
                if (hasFocus) {

                } else {

                }
                break;
            case ib_music :
                Log.d(TAG, "onFocusChange: music");
                if (hasFocus) {

                } else {

                }
                break;
            case ib_apk :
                Log.d(TAG, "onFocusChange: apk");
                if (hasFocus) {

                } else {

                }
                break;
            case ib_others :
                Log.d(TAG, "onFocusChange: others");
                if (hasFocus) {

                } else {

                }
                break;
        }
    }
}
