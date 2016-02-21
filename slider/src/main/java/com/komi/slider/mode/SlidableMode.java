package com.komi.slider.mode;

import android.view.View;

/**
 * Created by Komi on 2016/2/21.
 */
public abstract class SlidableMode {

    public static SlidableMode ALL = AllMode.getInstance();
    public static SlidableMode CUSTOM = CustomMode.getInstance();
    public static SlidableMode SINGLE = SingleMode.getInstance();

    public abstract View getSlidableChild(View touchChild);

    public abstract void removeSlidableChild(View child);

    public void addSlidableChild(View child) {

    }

    public void addCustomSlidableChild(View... children) {

    }

}
