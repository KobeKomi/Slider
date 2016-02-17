package com.komi.slider;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.komi.slider.ui.SliderActivityAdapter;
import com.komi.slider.ui.SliderFragmentAdapter;
import com.komi.slider.ui.SliderUi;


/**
 * Created by Komi on 2016-01-27.
 */
public class SliderUtils {

    public static ISlider attachActivity(Activity activity, SliderConfig config) {
        SliderActivityAdapter activityAdapter = new SliderActivityAdapter(activity);
        return attachUi(activityAdapter, config);
    }

    public static ISlider attachFragment(Fragment fragment, SliderConfig config,View rootView) {
        SliderFragmentAdapter fragmentAdapter = new SliderFragmentAdapter(fragment,rootView);
        return attachUi(fragmentAdapter, config);
    }

    public static ISlider attachUi(SliderUi ui, SliderConfig config) {
        ISlider iSlider = attachUi(ui, config, true, true, false);
        return iSlider;
    }

    /**
     * @param ui
     * @param config
     * @param matchParent 是否包裹内容
     * @param replace     是否替换底层的view
     */
    public static ISlider attachUi(final SliderUi ui, final SliderConfig config, boolean matchParent, boolean replace, boolean immediately) {
        if (ui == null) {
            return null;
        }
        // Setup the slider panel and attach it to the decor
        final Slider slider = new Slider(ui.getUiActivity(), null, config);
        //slider.setId(R.id.slidable_panel);

        final SliderListener sliderListener = slider.getConfig().getListener();

        // Set the panel slide listener for when it becomes closed or opened
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

        if (ui.isActivityUi()) {
            if (!replace) {
                addToActivity(slider, ui);
            } else {
                replaceToActivity(slider, ui, matchParent);
            }
        } else {

            View rootView = ui.getRootView();
            if (rootView.getParent() != null) {
//                ViewGroup viewGroup=((ViewGroup)rootView.getParent());
//                viewGroup.removeView(rootView);
//                slider.addView(rootView);
//                viewGroup.addView(slider);
//                slider.getConfig().setSlideEnter(false);
            }else {
                slider.addView(rootView);
            }
            slider.setDecorView(slider.getChildAt(0));
        }

        ISlider iSlider = proxyISlider(ui, slider.getConfig(), slider);
        iSlider.slideEnter(immediately);

        return iSlider;
    }

    private static ISlider proxyISlider(final SliderUi ui, final SliderConfig config, final Slider slider) {


        ISlider iSlider = new ISlider() {
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
                    if (ui.isActivityUi()) {
                        int aminId = config.getPosition().getActivitySlidingAmins()[1];
                        ui.getUiActivity().overridePendingTransition(0, aminId);
                        listenerActivity(ui, config, aminId, 1, 0);
                    } else {
                        slider.slideExit();
                    }
                }
            }

            @Override
            public void slideEnter(boolean immediately) {
                if (config.isSlideEnter()) {
                    if (ui.isActivityUi()) {
                        //View显示前为activity拉开的背景透明化。也可以在AndroidManifest里为activity配置android:theme为
                        //<item name="android:windowIsTranslucent">true</item>
                        // Utils.convertActivityToTranslucent(ui.getUiActivity());
                        int aminId = config.getPosition().getActivitySlidingAmins()[0];
                        ui.getUiActivity().overridePendingTransition(aminId, 0);
                        listenerActivity(ui, config, aminId, 0, 1);
                    } else {
                        if (immediately) {
                            slider.slideEnter();
                        } else {
                            slider.getViewTreeObserver().addOnGlobalLayoutListener(
                                    new ViewTreeObserver.OnGlobalLayoutListener() {
                                        public void onGlobalLayout() {
                                            slider.slideEnter();
                                        }
                                    }
                            );
                        }
                    }
                }

            }

            @Override
            public Slider getSliderView() {
                return slider;
            }
        };

        return iSlider;
    }


    /**
     * 直接作为activity的parent加入
     * 用 replaceToActivity替代
     */
    @Deprecated
    private static void addToActivity(Slider slider, SliderUi ui) {
        ViewGroup root = (ViewGroup) ui.getRootView();

        ui.getUiActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            root.setBackground(null);
        }
        ViewGroup decorChild = (ViewGroup) root.getChildAt(0);
        root.removeView(decorChild);
        slider.addView(decorChild);
        decorChild.setBackgroundColor(Color.WHITE);
        slider.setDecorView(decorChild);
        root.addView(slider, 0);
    }

    /**
     * 替换掉activity的parent,减少嵌套
     *
     * @param ui
     * @param matchParent
     */
    private static void replaceToActivity(Slider slider, SliderUi ui, boolean matchParent) {
        //用slider替换掉activity container parent减少一层嵌套
        //PhoneWindows--decor
        ViewGroup root = (ViewGroup) ui.getRootView();

        //activity拉开的背景透明化,==<item name="android:windowBackground">@android:color/transparent</item>

        ui.getUiActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            root.setBackground(null);
        }
        //decor child
        ViewGroup decorChild = (ViewGroup) root.getChildAt(0);
        //activity container parent
        ViewGroup content = (ViewGroup) decorChild.getChildAt(1);
        //activity container
        View container = content.getChildAt(0);

        if (matchParent) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            slider.setLayoutParams(layoutParams);
        }

        content.removeView(container);
        slider.addView(container);
        decorChild.removeView(content);
        slider.setDecorView(container);
        decorChild.addView(slider, 0);
        container.setBackgroundColor(Color.WHITE);

    }

    /**
     * 监听activity关闭与打开的状态
     */
    private static void listenerActivity(SliderUi ui, SliderConfig config, int aminId, float... values) {

        final boolean in = values[0] < values[values.length - 1];

        Animation animation = AnimationUtils.loadAnimation(ui.getUiActivity(), aminId);
        final SliderListener listener = config.getListener();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(values);
        valueAnimator.setDuration(animation.getDuration());
        valueAnimator.setInterpolator(animation.getInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                if (listener != null) {
                    listener.onSlideChange(currentValue);
                    if (currentValue >= 1 && in) {
                        listener.onSlideOpened();
                    }
                    if (currentValue == 0 && !in) {
                        listener.onSlideClosed();
                    }
                }

            }
        });
        valueAnimator.start();
    }


}
