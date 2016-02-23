package com.komi.slider.ui;

import android.app.Activity;
import android.view.View;

import com.komi.slider.Slider;

/**
 * Created by Komi on 2016-02-23.
 */
public class SliderViewAdapter extends SliderUi {


    private View view;

    public SliderViewAdapter(View view) {
        this.view = view;
    }

    @Override
    public Activity getUiActivity() {
        return (Activity) view.getContext();
    }


    @Override
    public void finishUi(Slider slider) {
        View slidableChild = slider.getSlidableChild();
        if (slidableChild != null) {
            slider.removeView(slidableChild);
        }
    }


    @Override
    public void addSlidableChild(Slider slider) {
        slider.getConfig().setScrimStartAlpha(0);
        slider.getConfig().setScrimEndAlpha(0);

        if (view != null) {
            slider.addView(view);
        }
        if (slider.getConfig().isSlideEnter()) {
            slideEnter(slider);
        }
    }

    @Override
    public void autoExit(Slider slider) {
        View slidableChild = slider.getSlidableChild();
        if (slidableChild != null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slidableChild, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), exitTarget[0], exitTarget[1]);
            slider.invalidate();
        }
    }

    public void slideEnter(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), enterTarget[0], enterTarget[1], child.getLeft(), child.getTop());
        }
    }
}
