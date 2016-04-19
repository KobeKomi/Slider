package com.komi.slider;

import com.komi.slider.ui.SliderUi;

/**
 * Created by Komi on 2016-01-21.
 */
public interface ISlider {
    void lock();

    void unlock();

    void setConfig(SliderConfig config);

    SliderConfig getConfig();

    void slideExit();

    Slider getSliderView();

    SliderUi getSliderUi();

    void updateConfig();
}
