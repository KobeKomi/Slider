package com.komi.sliderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.komi.slider.ISlider;
import com.komi.slider.SliderUtils;
import com.komi.slider.Utils;

public class AdapterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sliderBtn;
    private ISlider iSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        setTitle(this.getClass().getSimpleName());
        sliderBtn = (Button) findViewById(R.id.slider_btn);
        sliderBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.slider_btn:
                if (iSlider == null) {
                    iSlider = SliderUtils.attachActivity(this, null);
                    Utils.convertActivityToTranslucent(this);
                }
                break;
        }
    }

}
