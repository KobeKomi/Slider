package com.komi.slider.ui.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

import com.komi.slider.Slider;
import com.komi.slider.ui.SliderUi;

/**
 * Created by Komi on 2016-02-16.
 */
public class SliderFragmentAdapter extends SliderUi {

    private Fragment fragment;
    private View rootView;

    public SliderFragmentAdapter(Fragment fragment, View rootView) {
        this.fragment = fragment;
        this.rootView = rootView;
    }

    @Override
    public Activity getUiActivity() {
        return fragment.getActivity();
    }


    @Override
    public void slideEnterBefore(final Slider slider) {
        if (rootView.getParent() == null) {
            slider.addView(rootView);

            if (slider.getConfig().isSlideEnter()) {
                slider.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            public void onGlobalLayout() {
                                boolean isSlideEnter = slider.getConfig().isSlideEnter();
                                if (isSlideEnter) {
                                    slideEnter(slider);
                                }
                                slider.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        }
                );
            }
        }
    }

    @Override
    public void slideEnter(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), enterTarget[0], enterTarget[1], enterTarget[2], enterTarget[3]);
        }
    }

    @Override
    public void slideExit(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slider.getSlidableChild(), slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(child, exitTarget[2], exitTarget[3]);
        }
        slider.invalidate();
    }

    @Override
    public void slideExitAfter(Slider slider) {
        if (!fragment.isRemoving()) {
            getUiActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


}
