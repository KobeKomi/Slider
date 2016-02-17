package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderActivity extends Activity implements SliderUi {

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
        iSlider= SliderUtils.attachUi(this,null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        iSlider.slideExit();
    }

    @Override
    public final Activity getUiActivity() {
        return this;
    }

    @Override
    public final boolean isActivityUi() {
        return true;
    }

    @Override
    public boolean isFinishingUi() {
        return isFinishing();
    }

    @Override
    public  void finishUi() {
        finish();
    }

    @Override
    public View getRootView() {
        return getWindow().getDecorView();
    }



}
