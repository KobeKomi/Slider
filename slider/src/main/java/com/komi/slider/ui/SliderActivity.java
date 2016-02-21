package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderActivity extends Activity{

    protected ISlider iSlider;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        attachSlideUi();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        attachSlideUi();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        attachSlideUi();
    }

    private void attachSlideUi()
    {
        iSlider= SliderUtils.attachActivity(this,null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        iSlider.slideExit();
    }




}
