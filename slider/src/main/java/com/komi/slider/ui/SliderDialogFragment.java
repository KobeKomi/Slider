package com.komi.slider.ui;

import android.app.DialogFragment;
import android.os.Bundle;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderUtils;
import com.komi.slider.ui.adapter.SliderDialogAdapter;

/**
 * Created by Komi on 2016-03-02.
 */
public abstract class SliderDialogFragment extends DialogFragment{

    protected ISlider iSlider;
    protected SliderConfig mConfig;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iSlider = SliderUtils.attachDialog(getActivity(),getDialog(),mConfig);
    }

    public void setCanceledOnTouchOutside(boolean cancel)
    {
        getDialog().setCanceledOnTouchOutside(cancel);
        ((SliderDialogAdapter)iSlider.getSliderUi()).setTouchOutside(cancel);
    }


}
