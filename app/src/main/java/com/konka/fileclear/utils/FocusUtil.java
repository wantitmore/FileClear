package com.konka.fileclear.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user001 on 2017-8-28.
 */

public class FocusUtil {
    public static void focusListener (final ViewGroup view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(view.getChildCount() > 0){
                        view.getChildAt(0).requestFocus();

                    }
                }
            }
        });

    }
}
