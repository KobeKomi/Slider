package com.komi.slider;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.view.View;

import com.komi.slider.ui.SliderActivityAdapter;
import com.komi.slider.ui.SliderFragmentAdapter;
import com.komi.slider.ui.SliderUi;
import com.komi.slider.ui.SliderV4FragmentAdapter;
import com.komi.slider.ui.SliderViewAdapter;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderUtils {

    public static ISlider attachActivity(Activity activity, SliderConfig config) {
        return attachActivity(null,activity,true,config);
    }

    public static ISlider attachActivity(Slider slider,Activity activity, boolean replace,SliderConfig config) {
        SliderActivityAdapter sliderUi=new SliderActivityAdapter(activity);
        sliderUi.setReplace(replace);
        return attachUi(slider,sliderUi,config);
    }

    public static ISlider attachFragment(Fragment fragment,View rootView, SliderConfig config) {
        return attachFragment(null,fragment,rootView,config);
    }

    public static ISlider attachFragment(Slider slider,Fragment fragment,View rootView, SliderConfig config) {
        SliderUi sliderUi=new SliderFragmentAdapter(fragment,rootView);
        return attachUi(slider,sliderUi,config);
    }

    public static ISlider attachV4Fragment(android.support.v4.app.Fragment fragment, View rootView, SliderConfig config) {
        return attachV4Fragment(null,fragment,rootView,config);
    }

    public static ISlider attachV4Fragment(Slider slider, android.support.v4.app.Fragment fragment, View rootView, SliderConfig config) {
        SliderUi sliderUi=new SliderV4FragmentAdapter(fragment,rootView);
        return attachUi(slider,sliderUi,config);
    }

    public static ISlider attachView(Activity activity, SliderConfig config) {
        return attachView(activity,null,config);
    }

    public static ISlider attachView(Activity activity, Slider slider, SliderConfig config) {
        SliderViewAdapter sliderUi=new SliderViewAdapter(activity);
        return attachUi(slider,sliderUi,config);
    }


    private static ISlider attachUi(Slider slider,SliderUi ui, SliderConfig config) {
        if (ui == null) {
            return null;
        }
        if(slider==null) {
            slider = new Slider(ui.getUiActivity(), config);
        }
        ISlider iSlider = attachUi(ui, slider);
        return iSlider;
    }



    public static ISlider attachUi(final SliderUi ui, final Slider slider) {


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
            public void onSlideClosed() {
                if (sliderListener != null) {
                    sliderListener.onSlideClosed();
                }
                if(slider.getConfig().isFinishUi()) {
                    ui.slideAfter(slider);
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

        ui.slideBefore(slider);

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
                ui.slideExit(slider);
            }


            @Override
            public Slider getSliderView() {
                return slider;
            }
        };

        return iSlider;
    }






}
