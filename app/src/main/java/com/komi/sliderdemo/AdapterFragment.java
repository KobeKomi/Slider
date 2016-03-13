package com.komi.sliderdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderListener;
import com.komi.slider.SliderUtils;

/**
 * Created by Komi on 2016/2/16.
 */
public class AdapterFragment extends Fragment implements FragmentCloseListener,SliderListener{

    private ISlider iSlider;
    private View rootView;
    private SliderConfig mConfig;
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_adapter, container, false);
        tv=(TextView)rootView.findViewById(R.id.adapter_fragment_tv);
        iSlider = SliderUtils.attachFragment(this,rootView, null);
        return iSlider.getSliderView();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConfig=iSlider.getConfig();
        mConfig.setSliderListener(this);
    }

    @Override
    public void fragmentClosed() {
        if(iSlider!=null)
            iSlider.slideExit();
    }

    @Override
    public void onSlideStateChanged(int state) {

    }

    @Override
    public void onSlideChange(float percent) {

    }

    @Override
    public void onSlideOpened() {

    }

    @Override
    public void onSlideClosed() {

    }
}
