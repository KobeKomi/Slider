package com.komi.slider;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Build;
import android.view.View;

import com.komi.slider.ui.adapter.SliderActivityAdapter;
import com.komi.slider.ui.adapter.SliderDialogAdapter;
import com.komi.slider.ui.SliderUi;
import com.komi.slider.ui.adapter.support.SliderFragmentAdapter;
import com.komi.slider.ui.adapter.SliderViewAdapter;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderUtils {

    public static ISlider attachActivity(Activity activity, SliderConfig config) {
        return attachActivity(null, activity, config);
    }

    public static ISlider attachActivity(Slider slider, Activity activity, SliderConfig config) {
        SliderUi sliderUi = new SliderActivityAdapter(activity);
        return attachUi(slider, sliderUi, config);
    }

    public static ISlider attachFragment(Fragment fragment, View rootView, SliderConfig config) {
        return attachFragment(null, fragment, rootView, config);
    }

    public static ISlider attachFragment(Slider slider, Fragment fragment, View rootView, SliderConfig config) {
        SliderUi sliderUi = new com.komi.slider.ui.adapter.SliderFragmentAdapter(fragment, rootView);
        return attachUi(slider, sliderUi, config);
    }

    public static ISlider attachV4Fragment(android.support.v4.app.Fragment fragment, View rootView, SliderConfig config) {
        return attachV4Fragment(null, fragment, rootView, config);
    }

    public static ISlider attachV4Fragment(Slider slider, android.support.v4.app.Fragment fragment, View rootView, SliderConfig config) {
        SliderUi sliderUi = new SliderFragmentAdapter(fragment, rootView);
        return attachUi(slider, sliderUi, config);
    }

    public static ISlider attachDialog(Activity activity, Dialog dialog, SliderConfig config) {
        return attachDialog(activity, null, dialog, config);
    }

    public static ISlider attachDialog(Activity activity, Slider slider, Dialog dialog, SliderConfig config) {
        SliderUi sliderUi = new SliderDialogAdapter(activity, dialog);
        return attachUi(slider, sliderUi, config);
    }


    public static ISlider attachView(Activity activity, SliderConfig config) {
        return attachView(activity, null, config);
    }

    public static ISlider attachView(Activity activity, Slider slider, SliderConfig config) {
        SliderViewAdapter sliderUi = new SliderViewAdapter(activity);
        return attachUi(slider, sliderUi, config);
    }


    public static ISlider attachUi(Slider slider, SliderUi ui, SliderConfig config) {
        if (ui == null) {
            return null;
        }
        if (slider == null) {
            slider = new Slider(ui.getUiActivity(), config);
        }
        ISlider iSlider = attachUi(slider,ui);
        return iSlider;
    }


    public static ISlider attachUi(final Slider slider,final SliderUi ui) {
        slider.setSliderConfigListener(new SliderConfigListener() {
            final ArgbEvaluator mEvaluator = new ArgbEvaluator();
            @Override
            public void onSlideClosed() {
                if (slider.getConfig().isFinishUi()) {
                    ui.slideExitAfter(slider);
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
            }
        });

        ui.slideEnterBefore(slider);

        return proxyISlider(ui, slider);
    }

    private static ISlider proxyISlider(final SliderUi ui, final Slider slider) {


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
                if (slider.getConfig().isSlideExit()) {
                    ui.slideExit(slider);
                } else {
                    if (slider.getConfig().isFinishUi()) {
                        ui.slideExitAfter(slider);
                    }
                }
            }

            @Override
            public Slider getSliderView() {
                return slider;
            }

            @Override
            public SliderUi getSliderUi() {
                return ui;
            }

            @Override
            public void updateConfig() {
                slider.setConfig(getConfig());
            }
        };

        return iSlider;
    }


}
