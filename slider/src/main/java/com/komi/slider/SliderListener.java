package com.komi.slider;

/**
 * This listener interface is for receiving events from the sliding panel such as state changes
 * and slide progress
 * 
 * Created by Komi on 2016-01-21.
 */
public interface SliderListener {

    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    void onSlideClosed();

}
