package com.komi.slider.ui;

import android.app.Activity;

import com.komi.slider.Slider;

/**
 * Created by Komi on 2016-01-27.
 */
public abstract class SliderUi {

    public abstract Activity getUiActivity();


    public abstract void slideBefore(Slider slider);


    public abstract void slideAfter(Slider slider);


    public abstract void slideExit(Slider slider);





}
