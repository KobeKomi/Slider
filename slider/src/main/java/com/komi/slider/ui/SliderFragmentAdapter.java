package com.komi.slider.ui;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

/**
 * Created by Komi on 2016-02-16.
 */
public class SliderFragmentAdapter implements SliderUi {

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
    public boolean isActivityUi() {
        return false;
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
}
