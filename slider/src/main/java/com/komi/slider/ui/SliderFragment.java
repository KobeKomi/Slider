package com.komi.slider.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderUtils;

/**
 * Created by Komi on 2016-02-05.
 */
public abstract class SliderFragment extends Fragment{

    private View rootView;
    protected ISlider iSlider;
    protected SliderConfig mConfig;


    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=creatingView(inflater,container,savedInstanceState);
        iSlider = SliderUtils.attachFragment(this, rootView,mConfig);
        if (mConfig == null) {
            mConfig=iSlider.getConfig();
        }
        return iSlider.getSliderView();
    }

    public abstract View creatingView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);
}
