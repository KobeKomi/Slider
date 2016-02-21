package com.komi.slider.mode;

import android.util.Log;
import android.view.View;

/**
 * Created by Komi on 2016/2/21.
 */
public class SingleMode extends SlidableMode {
    private static SingleMode ourInstance = new SingleMode();

    protected View slidableChild;

    public static SingleMode getInstance() {
        return ourInstance;
    }

    private SingleMode() {
    }


    @Override
    public void addSlidableChild(View child) {
        this.slidableChild=child;
    }

    @Override
    public View getSlidableChild(View touchChild) {
        return slidableChild;
    }

    @Override
    public void removeSlidableChild(View child) {
        this.slidableChild=null;
    }
}
