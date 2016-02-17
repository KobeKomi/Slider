package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;

/**
 * Created by Komi on 2016-01-27.
 */
public interface SliderUi {

    /**
     * 获取Ui的activity
     * @return
     */
    Activity getUiActivity();

    /**
     * Ui是否为activity
     * @return
     */
    boolean isActivityUi();

    /**
     * 是否正在关闭Ui
     * @return
     */
    boolean isFinishingUi();

    /**
     * 关闭Ui的操作
     */
    void finishUi();

    /**
     * 获取Ui最外层父控件
     * @return
     */
    View getRootView();

}
