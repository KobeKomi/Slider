package com.komi.slider.ui;

import android.app.Activity;

import com.komi.slider.Slider;

/**
 * Created by Komi on 2016-01-27.
 */
public abstract class SliderUi {

    /**
     * @return 获取实现SliderUi的activity
     */
    public abstract Activity getUiActivity();


    /**
     * 关闭SliderUi对象的操作
     */
    public abstract void finishUi(Slider slider);


    public abstract void autoExit(Slider slider);

    public abstract void addSlidableChild(Slider slider);




}
