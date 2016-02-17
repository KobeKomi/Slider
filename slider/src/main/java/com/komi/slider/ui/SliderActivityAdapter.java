package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;

/**
 * Created by Komi on 2016-02-16.
 */
public class SliderActivityAdapter implements SliderUi{


    private Activity activity;

    public SliderActivityAdapter(Activity activity)
    {
        this.activity=activity;
    }

    @Override
    public Activity getUiActivity() {
        return activity;
    }


    @Override
    public boolean isActivityUi() {
        return true;
    }


    @Override
    public boolean isFinishingUi() {
        return activity.isFinishing();
    }


    @Override
    public void finishUi() {
        activity.finish();
    }

    @Override
    public View getRootView() {
        return activity.getWindow().getDecorView();
    }
}
