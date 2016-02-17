package com.komi.slider;

/**
 * Created by Komi on 2016-01-21.
 */
public interface ISlider {
    void lock();

    void unlock();

    void setConfig(SliderConfig config);

    SliderConfig getConfig();

    void slideExit();

    /**
     * @param immediately
     *
     * 针对非activity界面，为true时，立即自动滑动进入界面，但可能会无效，因为view的大小还没初始化
     */
    void slideEnter(boolean immediately);

    Slider getSliderView();
}
