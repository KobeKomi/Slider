package com.komi.slider.mode;

import android.view.View;

/**
 * Created by Komi on 2016/2/21.
 */
public class AllMode extends SlidableMode {
    private static AllMode ourInstance = new AllMode();


    public static AllMode getInstance() {
        return ourInstance;
    }

    private AllMode() {

    }

    @Override
    public View getSlidableChild(View touchChild) {
        return touchChild;
    }




}
