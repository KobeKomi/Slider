package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;

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
     * @return 是否正在关闭SliderUi对象
     */
    public abstract boolean isFinishingUi();

    /**
     * 关闭SliderUi对象的操作
     */
    public abstract void finishUi();

    /**
     * @return 获取SliderUi对象最外层父控件
     */
    public abstract View getRootView();


    public abstract void addSlidableChild(Slider slider);

    public abstract void slideExit(Slider slider);

    public abstract void slideEnter(Slider slider,boolean immediately);


}
