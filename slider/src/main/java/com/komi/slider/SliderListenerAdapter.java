package com.komi.slider;

import android.view.View;

/**
 * 
 *Created by Komi on 2016-01-21.
 */
public class SliderListenerAdapter implements SliderListener {
    @Override public void onSlideStateChanged(int state) {}
    @Override public void onSlideChange(float percent) {}
    @Override public void onSlideOpened() {}
    @Override public void onSlideClosed(View slidableChild) {}
}
