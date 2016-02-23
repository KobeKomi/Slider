package com.komi.slider;

/**
 * Created by Komi on 2016-01-21.
 */
public interface ISlider {
    void lock();

    void unlock();

    void setConfig(SliderConfig config);

    SliderConfig getConfig();

    void autoExit();

    Slider getSliderView();
}
