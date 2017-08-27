package com.konka.fileclear.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.konka.fileclear.R;

/**
 * Created by user001 on 2017-8-27.
 */

public class AnimUtil {
private static final String TAG ="AnimUtil";
    public static void focueAnim(Context context, View v, boolean hasFocus) {
        Log.d(TAG, "focueAnim: ------------");
        if (hasFocus) {
            Animation foucusAnim = AnimationUtils.loadAnimation(context, R.anim.focus_on);
            v.startAnimation(foucusAnim);
        } else {
            Animation foucusAnim = AnimationUtils.loadAnimation(context, R.anim.focus_off);
            v.startAnimation(foucusAnim);
        }
    }
}
