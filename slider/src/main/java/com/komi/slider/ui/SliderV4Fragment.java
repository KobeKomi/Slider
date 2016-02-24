package com.komi.slider.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;

/**
 * Created by Komi on 2016-02-24.
 */
public abstract class SliderV4Fragment extends Fragment{

    private View rootView;

    protected ISlider iSlider;


    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=creatingView(inflater,container,savedInstanceState);
        iSlider = SliderUtils.attachV4Fragment(this, rootView,null);
        return iSlider.getSliderView();
    }

    public abstract View creatingView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);
}
