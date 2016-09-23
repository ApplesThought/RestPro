package com.example.view.customview;

import android.view.View;

public interface OnSlideListener {
    public static final int SLIDE_STATUS_OFF = 0;
    public static final int SLIDE_STATUS_START_SCROLL = 1;
    public static final int SLIDE_STATUS_ON = 2;

    /**
     * @param view   current SlideView
     * @param status SLIDE_STATUS_ON or SLIDE_STATUS_OFF
     */
    public void onSlide(View view, int status);
}
