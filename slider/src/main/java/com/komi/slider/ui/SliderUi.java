package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;

/**
 * Created by Komi on 2016-01-27.
 */
public interface SliderUi {

    /**
     * @return 获取实现SliderUi的activity
     */
    Activity getUiActivity();

    /**
     * @return 实现SliderUi对象是否为activity
     */
    boolean isActivityUi();

    /**
     * @return 是否正在关闭SliderUi对象
     */
    boolean isFinishingUi();

    /**
     * 关闭SliderUi对象的操作
     */
    void finishUi();

    /**
     * @return 获取SliderUi对象最外层父控件
     */
    View getRootView();

}
