package com.komi.slider.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.komi.slider.Slider;
import com.komi.slider.SliderConfig;
import com.komi.slider.SliderListener;

/**
 * Created by Komi on 2016-02-16.
 */
public class SliderActivityAdapter extends SliderUi {

    private Activity activity;
    private boolean replace=true;

    public SliderActivityAdapter(Activity activity)
    {
        this.activity=activity;

    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    @Override
    public Activity getUiActivity() {
        return activity;
    }


    @Override
    public boolean isFinishingUi() {
        return activity.isFinishing();
    }


    @Override
    public void finishUi() {
        activity.finish();
    }

    @Override
    public View getRootView() {
        return activity.getWindow().getDecorView();
    }

    @Override
    public void addSlidableChild(Slider slider) {
        if (!replace) {
            addToActivity(slider);
        } else {
            replaceToActivity(slider);
        }
    }

    @Override
    public void slideExit(Slider slider) {
        int aminId = slider.getConfig().getPosition().getActivitySlidingAmins()[1];
        getUiActivity().overridePendingTransition(0, aminId);
        listenerActivity(slider.getConfig(), aminId, 1, 0);
    }

    @Override
    public void slideEnter(Slider slider,boolean immediately) {
        //View显示前为activity拉开的背景透明化。也可以在AndroidManifest里为activity配置android:theme为
        //<item name="android:windowIsTranslucent">true</item>
        // Utils.convertActivityToTranslucent(ui.getUiActivity());
        int aminId = slider.getConfig().getPosition().getActivitySlidingAmins()[0];
        getUiActivity().overridePendingTransition(aminId, 0);
        listenerActivity(slider.getConfig(), aminId, 0, 1);

    }

    //直接作为activity的parent加入用 replaceToActivity替代
    @Deprecated
    private  void addToActivity(Slider slider) {
        ViewGroup root = (ViewGroup)getRootView();

        getUiActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            root.setBackground(null);
        }
        ViewGroup decorChild = (ViewGroup) root.getChildAt(0);
        root.removeView(decorChild);
        slider.addView(decorChild);
        decorChild.setBackgroundColor(Color.WHITE);
        root.addView(slider, 0);
    }

    /**
     * 替换掉activity的parent,减少嵌套
     */
    private  void replaceToActivity(Slider slider) {
        //用slider替换掉activity container parent减少一层嵌套
        //PhoneWindows--decor
        ViewGroup root = (ViewGroup)getRootView();

        //activity拉开的背景透明化,==<item name="android:windowBackground">@android:color/transparent</item>

        getUiActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            slider.setLayoutParams(layoutParams);

        content.removeView(container);
        slider.addView(container);
        decorChild.removeView(content);
        decorChild.addView(slider, 0);
        container.setBackgroundColor(Color.WHITE);

    }

    // 监听activity关闭与打开的状态
    private void listenerActivity(SliderConfig config, int aminId, float... values) {

        final boolean in = values[0] < values[values.length - 1];

        Animation animation = AnimationUtils.loadAnimation(getUiActivity(), aminId);
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
                        listener.onSlideClosed(getRootView());
                    }
                }

            }
        });
        valueAnimator.start();
    }
}
