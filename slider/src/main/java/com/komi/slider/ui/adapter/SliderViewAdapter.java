package com.komi.slider.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.komi.slider.Slider;
import com.komi.slider.ui.SliderUi;

/**
 * Created by Komi on 2016-02-23.
 */
public class SliderViewAdapter extends SliderUi {


    private Activity activity;

    public SliderViewAdapter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public Activity getUiActivity() {
        return activity;
    }


    @Override
    public void slideEnterBefore(Slider slider) {
        if (slider.getConfig().isSlideEnter()) {
            slideEnter(slider);
        }
    }

    @Override
    public void slideEnter(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), enterTarget[0], enterTarget[1], child.getLeft(), child.getTop());
        }
    }


    @Override
    public void slideExit(Slider slider) {
        View slidableChild = slider.getSlidableChild();
        if (slidableChild != null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slidableChild, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), exitTarget[0], exitTarget[1]);
            slider.invalidate();
        }

    }


    @Override
    public void slideExitAfter(Slider slider) {
        View slidableChild = slider.getSlidableChild();
        if (slidableChild != null) {
            slider.removeView(slidableChild);
        }
    }
}
