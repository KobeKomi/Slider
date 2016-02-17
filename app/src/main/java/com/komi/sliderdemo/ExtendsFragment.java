package com.komi.sliderdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.ui.SliderFragment;

/**
 * Created by Komi on 2016-02-05.
 */
public class ExtendsFragment extends SliderFragment implements FragmentCloseListener{

    @Override
    public View creatingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entends, container, false);
    }

    @Override
    public void fragmentClosed() {
        iSlider.slideExit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("KOMI","----------onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("KOMI","-------onDestroy");

    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("KOMI","-------onDetach");

    }
}
