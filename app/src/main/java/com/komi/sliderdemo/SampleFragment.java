package com.komi.sliderdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderListener;
import com.komi.slider.SliderUtils;
import com.komi.slider.position.SliderPosition;

import java.util.Random;


/**
 * Created by Komi on 2016-02-03.
 */
public class SampleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener,FragmentCloseListener{

    private SliderConfig mConfig;
    private ISlider iSlider;
    private RadioGroup radioGroup;
    private Switch edgeSwitch;
    private TextView counterTv;
    private boolean firstTimeOpened;

    private View rootView;
    private int []RadioBtnIds={R.id.fragment_rbt_left,R.id.fragment_rbt_right,R.id.fragment_rbt_top,
            R.id.fragment_rbt_bottom,R.id.fragment_rbt_vertical,R.id.fragment_rbt_horizontal,R.id.fragment_rbt_all};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        radioGroup=(RadioGroup)rootView.findViewById(R.id.fragment_radio_group);
        counterTv=(TextView)rootView.findViewById(R.id.fragment_counter_tv);
        Random random =new Random();
        int position= random.nextInt(SliderPosition.sPositionChildren.length);

        SliderPosition sliderPosition= SliderPosition.sPositionChildren[position];
        ((RadioButton)rootView.findViewById(RadioBtnIds[position])).setChecked(true);

        radioGroup.setOnCheckedChangeListener(this);

        mConfig = new SliderConfig.Builder()
                .position(sliderPosition)
                .velocityThreshold(2400)
                .distanceThreshold(.25f)
                .listener(listener)
                .edge(false)
                .build();

        iSlider = SliderUtils.attachFragment(this, rootView,mConfig);
        edgeSwitch = (Switch)rootView.findViewById(R.id.fragment_swich);
        edgeSwitch.setOnCheckedChangeListener(this);
        edgeSwitch.setChecked(mConfig.isEdgeOnly());

        return iSlider.getSliderView();
    }

    private SliderListener listener=new SliderListener() {
        @Override
        public void onSlideStateChanged(int state) {
        }

        @Override
        public void onSlideChange(float percent) {

        }

        @Override
        public void onSlideOpened() {
            if(!firstTimeOpened) {
                counterTv.setVisibility(View.VISIBLE);
                counterTv.setText("onSlideOpened");
                firstTimeOpened=true;
            }
        }

        @Override
        public void onSlideClosed() {
            firstTimeOpened=false;

        }
    };


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SliderPosition position;
        switch (checkedId)
        {
            default:
            case R.id.fragment_rbt_left:
                position= SliderPosition.LEFT;
                break;
            case R.id.fragment_rbt_right:
                position= SliderPosition.RIGHT;
                break;
            case R.id.fragment_rbt_bottom:
                position= SliderPosition.BOTTOM;
                break;
            case R.id.fragment_rbt_top:
                position= SliderPosition.TOP;
                break;
            case R.id.fragment_rbt_horizontal:
                position= SliderPosition.HORIZONTAL;
                break;
            case R.id.fragment_rbt_vertical:
                position= SliderPosition.VERTICAL;
                break;
            case R.id.fragment_rbt_all:
                position= SliderPosition.ALL;
                break;
        }
        mConfig.setPosition(position);
        iSlider.setConfig(mConfig);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fragment_swich:
                mConfig.setEdgeOnly(isChecked);
                iSlider.setConfig(mConfig);
                break;
        }
    }

    @Override
    public void fragmentClosed() {
        iSlider.slideExit();
    }
}
