package com.komi.slider;

import android.view.MotionEvent;

/**
 * This interface is for receiving events from the slider such as state changes
 * 
 * Created by Komi on 2016-01-21.
 */
public interface SliderListener {

    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    void onSlideClosed();

    boolean customSlidable(MotionEvent event);

}
