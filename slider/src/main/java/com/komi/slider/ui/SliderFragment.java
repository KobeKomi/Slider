package com.komi.slider.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;

/**
 * Created by Komi on 2016-02-05.
 */
public abstract class SliderFragment extends Fragment implements SliderUi {

    private View rootView;

    protected ISlider iSlider;


    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=creatingView(inflater,container,savedInstanceState);
        iSlider = SliderUtils.attachUi(this, null);
        return iSlider.getSliderView();
    }

    @Override
    public final Activity getUiActivity() {
        return getActivity();
    }

    @Override
    public final boolean isActivityUi() {
        return false;
    }

    @Override
    public boolean isFinishingUi() {
        return isRemoving();
    }

    @Override
    public void finishUi() {
        getUiActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public View getRootView() {
        return rootView;
    }


    public abstract View creatingView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);
}
