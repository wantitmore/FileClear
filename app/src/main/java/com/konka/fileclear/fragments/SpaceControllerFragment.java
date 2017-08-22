package com.konka.fileclear.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.konka.fileclear.R;
import com.konka.fileclear.activity.AudioActivity;
import com.konka.fileclear.common.MediaResourceManager;

import static com.konka.fileclear.R.id.ib_deep_clear;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceControllerFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener{

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
        mDeepClear.setNextFocusUpId(R.id.rb_space_controller);
        initListener();
        return view;
    }

    private void initListener() {
        mDeepClear.setOnFocusChangeListener(this);
        mDeepClear.setOnClickListener(this);
        mImages.setOnFocusChangeListener(this);
        mImages.setOnClickListener(this);
        mVideo.setOnFocusChangeListener(this);
        mVideo.setOnClickListener(this);
        mMusic.setOnFocusChangeListener(this);
        mMusic.setOnClickListener(this);
        mApk.setOnFocusChangeListener(this);
        mApk.setOnClickListener(this);
        mOthers.setOnFocusChangeListener(this);
        mOthers.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        focueAnim(v, hasFocus);
    }

    private void focueAnim(View v, boolean hasFocus) {
        if (hasFocus) {
            Animation foucusAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.focus_on);
            v.startAnimation(foucusAnim);
        } else {
            Animation foucusAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.focus_off);
            v.startAnimation(foucusAnim);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_deep_clear :

                break;
            case R.id.ib_image :

                break;
            case R.id.ib_video :

                break;
            case R.id.ib_music :
//                SearchUtil.getSupportFileList(getActivity(), new String[]{".mp3"});
                MediaResourceManager.getAudiosFromMedia(getActivity());
                startActivity(new Intent(getActivity(), AudioActivity.class));
                break;
            case R.id.ib_apk :

                break;
            case R.id.ib_others :

                break;
        }
    }
}
