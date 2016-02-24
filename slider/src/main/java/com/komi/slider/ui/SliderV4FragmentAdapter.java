package com.komi.slider.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;

import com.komi.slider.Slider;

/**
 * Created by Komi on 2016-02-24.
 */
public class SliderV4FragmentAdapter extends SliderUi {

    private Fragment fragment;
    private View rootView;

    public SliderV4FragmentAdapter(Fragment fragment, View rootView) {
        this.fragment = fragment;
        this.rootView = rootView;
    }

    @Override
    public FragmentActivity getUiActivity() {
        return fragment.getActivity();
    }


    @Override
    public void slideAfter(Slider slider) {
        if (!fragment.isRemoving()) {
            FragmentTransaction transaction = getUiActivity().getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment).commit();
        }

    }

    @Override
    public void slideBefore(final Slider slider) {
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
    public void slideExit(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slider.getSlidableChild(), slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(child, exitTarget[0], exitTarget[1]);

        }
        slider.invalidate();
    }


    public void slideEnter(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child, slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), enterTarget[0], enterTarget[1], child.getLeft(), child.getTop());
        }
    }


}
