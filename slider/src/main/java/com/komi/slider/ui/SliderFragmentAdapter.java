package com.komi.slider.ui;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

import com.komi.slider.Slider;

/**
 * Created by Komi on 2016-02-16.
 */
public class SliderFragmentAdapter extends SliderUi {

    private Fragment fragment;
    private View rootView;
    public SliderFragmentAdapter(Fragment fragment,View rootView) {
        this.fragment = fragment;
        this.rootView=rootView;
    }

    @Override
    public Activity getUiActivity() {
        return fragment.getActivity();
    }


    @Override
    public boolean isFinishingUi() {
        return fragment.isRemoving();
    }

    @Override
    public void finishUi() {
        getUiActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
    }


    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void addSlidableChild(Slider slider) {
        View rootView = getRootView();
        if (rootView.getParent() != null) {
//                ViewGroup viewGroup=((ViewGroup)rootView.getParent());
//                viewGroup.removeView(rootView);
//                slider.addView(rootView);
//                viewGroup.addView(slider);
//                slider.getConfig().setSlideEnter(false);
        }else {
            slider.addView(rootView);
        }
    }

    @Override
    public void slideExit(Slider slider) {
        slider.slideExit();
    }

    @Override
    public void slideEnter(final Slider slider,boolean immediately) {
        if (immediately) {
            slider.slideEnter();
        } else {
            slider.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            slider.slideEnter();
                        }
                    }
            );
        }
    }
}
