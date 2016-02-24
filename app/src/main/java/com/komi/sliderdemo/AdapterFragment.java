package com.komi.sliderdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;

/**
 * Created by Komi on 2016/2/16.
 */
public class AdapterFragment extends Fragment implements FragmentCloseListener{

    private ISlider iSlider;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_adapter, container, false);
        iSlider = SliderUtils.attachFragment(this,rootView, null);
        return iSlider.getSliderView();
    }

    @Override
    public void fragmentClosed() {
        if(iSlider!=null)
        {
            iSlider.slideExit();
        }
    }

}
