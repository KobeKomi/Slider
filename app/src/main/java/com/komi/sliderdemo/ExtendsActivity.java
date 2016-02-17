package com.komi.sliderdemo;

import android.os.Bundle;

import com.komi.slider.ui.SliderActivity;

public class ExtendsActivity extends SliderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extends);

    }
}
