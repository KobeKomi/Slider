package com.komi.sliderdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.komi.slider.ISlider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderListener;
import com.komi.slider.SliderUtils;
import com.komi.slider.position.SliderPosition;

import java.util.Random;

/**
 * Created by Komi on 2016/2/03.
 */
public class SampleActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private SliderConfig mConfig;
    private Switch edgeSwitch;
    private ISlider iSlider;
    private RadioGroup radioGroup;

    private int[] RadioBtnIds = {R.id.rbt_left, R.id.rbt_right, R.id.rbt_top, R.id.rbt_bottom,R.id.rbt_horizontal, R.id.rbt_vertical, R.id.rbt_all};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        setTitle(Demo.SAMPLE_ACTIVITY.titleResId);


        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        Random random = new Random();
        int position = random.nextInt(SliderPosition.sPositionChildren.length);
        SliderPosition sliderPosition =SliderPosition.sPositionChildren[position];
        ((RadioButton) findViewById(RadioBtnIds[position])).setChecked(true);

        int primaryColor = getResources().getColor(R.color.light_blue_500);

        mConfig = new SliderConfig.Builder()
                .primaryColor(primaryColor)
                .secondaryColor(Color.TRANSPARENT)
                .position(sliderPosition)
                .edge(false)
                .listener(listener)
                .build();

        iSlider = SliderUtils.attachActivity(this, mConfig);

        edgeSwitch = (Switch) findViewById(R.id.swich);
        edgeSwitch.setOnCheckedChangeListener(this);
        edgeSwitch.setChecked(mConfig.isEdgeOnly());
        radioGroup.setOnCheckedChangeListener(this);

    }

    private SliderListener listener = new SliderListener() {
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
    };


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SliderPosition position;

        switch (checkedId) {
            default:
            case R.id.rbt_left:
                position = SliderPosition.LEFT;
                break;
            case R.id.rbt_right:
                position = SliderPosition.RIGHT;
                break;
            case R.id.rbt_bottom:
                position = SliderPosition.BOTTOM;
                break;
            case R.id.rbt_top:
                position = SliderPosition.TOP;
                break;
            case R.id.rbt_horizontal:
                position = SliderPosition.HORIZONTAL;
                break;
            case R.id.rbt_vertical:
                position = SliderPosition.VERTICAL;
                break;
            case R.id.rbt_all:
                position = SliderPosition.ALL;
                break;
        }
        mConfig.setPosition(position);
        iSlider.setConfig(mConfig);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swich:
                mConfig.setEdgeOnly(isChecked);
                iSlider.setConfig(mConfig);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        iSlider.slideExit();
    }

}
