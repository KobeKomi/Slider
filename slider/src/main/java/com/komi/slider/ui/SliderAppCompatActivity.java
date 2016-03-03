package com.komi.slider.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderUtils;


/**
 * Created by Komi on 2016-02-24.
 */
public class SliderAppCompatActivity extends AppCompatActivity{

    protected ISlider iSlider;
    protected SliderConfig mConfig;

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
        iSlider= SliderUtils.attachActivity(this,mConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        iSlider.slideExit();
    }




}
