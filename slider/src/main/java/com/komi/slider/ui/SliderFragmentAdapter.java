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
    public void finishUi(Slider slider) {
        if(!fragment.isRemoving())
        getUiActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public void addSlidableChild(final Slider slider) {
        if (rootView.getParent() == null) {
            slider.addView(rootView);

            if(slider.getConfig().isSlideEnter()) {
                slider.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            public void onGlobalLayout() {
                                slideEnter(slider);
                                slider.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        }
                );
            }
        }
    }

    @Override
    public void autoExit(Slider slider) {
        View child=slider.getSlidableChild();
        if(child!=null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slider.getSlidableChild(), slider.getWidth(), slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(child,exitTarget[0], exitTarget[1]);

        }
        slider.invalidate();
    }


    public void slideEnter(Slider slider)
    {
        View child=slider.getSlidableChild();
        if(child!=null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child,slider.getWidth(),slider.getHeight());
            slider.getViewDragHelper().smoothSlideViewTo(slider.getSlidableChild(), enterTarget[0],enterTarget[1],child.getLeft(),child.getTop());
        }
    }


}
