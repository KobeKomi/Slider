package com.komi.slider;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.view.View;

import com.komi.slider.ui.SliderUi;
import com.komi.slider.ui.SliderActivityAdapter;
import com.komi.slider.ui.SliderFragmentAdapter;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderUtils {

    public static ISlider attachActivity(Activity activity, SliderConfig config) {
        SliderUi sliderUi=new SliderActivityAdapter(activity);
        return attachUi(sliderUi,config);
    }

    public static ISlider attachFragment(Fragment fragment,View rootView, SliderConfig config) {
        SliderUi sliderUi=new SliderFragmentAdapter(fragment,rootView);
        return attachUi(sliderUi,config);
    }

    private static ISlider attachUi(SliderUi ui, SliderConfig config) {
        if (ui == null) {
            return null;
        }
        final Slider slider = new Slider(ui.getUiActivity(),config);
        ISlider iSlider = attachUi(ui, slider,false);
        return iSlider;
    }



    public static ISlider attachUi(final SliderUi ui, final Slider slider , boolean immediately) {


        final SliderListener sliderListener = slider.getConfig().getListener();

        slider.setOnPanelSlideListener(new SliderListener() {

            private final ArgbEvaluator mEvaluator = new ArgbEvaluator();

            @Override
            public void onSlideStateChanged(int state) {
                if (sliderListener != null) {
                    sliderListener.onSlideStateChanged(state);
                }
            }

            @Override
            public void onSlideOpened() {
                if (sliderListener != null) {
                    sliderListener.onSlideOpened();
                }
            }

            @Override
            public void onSlideClosed(View slidableChild) {
                if (sliderListener != null) {
                    sliderListener.onSlideClosed(slidableChild);
                }

                if (!ui.isFinishingUi()) {
                    ui.finishUi();
                }

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSlideChange(float percent) {
                // Interpolate the statusbar color
                // TODO: Add support for KitKat
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        slider.getConfig().areStatusBarColorsValid()) {
                    int newColor = (int) mEvaluator.evaluate(percent, slider.getConfig().getSecondaryColor(),
                            slider.getConfig().getPrimaryColor());
                    ui.getUiActivity().getWindow().setStatusBarColor(newColor);
                }

                if (sliderListener != null) {
                    sliderListener.onSlideChange(percent);
                }
            }
        });


        ui.addSlidableChild(slider);
        ISlider iSlider = proxyISlider(ui, slider.getConfig(), slider);
        iSlider.slideEnter(immediately);

        return iSlider;
    }

    private static ISlider proxyISlider(final SliderUi ui, final SliderConfig config, final Slider slider) {


        final ISlider iSlider = new ISlider() {
            @Override
            public void lock() {
                slider.lock();
            }

            @Override
            public void unlock() {
                slider.unlock();
            }

            @Override
            public void setConfig(SliderConfig config) {
                slider.setConfig(config);
            }

            @Override
            public SliderConfig getConfig() {
                return slider.getConfig();
            }


            @Override
            public void slideExit() {
                if (config.isSlideExit()) {
                   ui.slideExit(slider);
                }
            }

            @Override
            public void slideEnter(boolean immediately) {
                if (config.isSlideEnter()) {
                    ui.slideEnter(slider,immediately);
                }

            }
            @Override
            public Slider getSliderView() {
                return slider;
            }
        };

        return iSlider;
    }






}
