package com.komi.slider.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.komi.slider.Slider;
import com.komi.slider.SliderListener;
import com.komi.slider.ui.SliderUi;

/**
 * Created by Komi on 2016-03-02.
 */
public class SliderDialogAdapter extends SliderUi {

    private Dialog dialog;
    private Activity activity;
    private boolean touchSlidableChild = true;
    private boolean touchOutside;
    private AnimatorSet animatorSet = new AnimatorSet();
    private DisplayMetrics dm;
    private FrameLayout.LayoutParams layoutParams;

    public SliderDialogAdapter(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
        dm = activity.getResources().getDisplayMetrics();
    }

    public boolean isTouchOutside() {
        return touchOutside;
    }

    public void setTouchOutside(boolean touchOutside) {
        this.touchOutside = touchOutside;
    }

    public FrameLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    @Override
    public Activity getUiActivity() {
        return activity;
    }

    @Override
    public void slideEnterBefore(Slider slider) {
        if (dialog != null) {
            attachToDialog(slider);
        }
        if (slider.getConfig().isSlideEnter()) {
            slideEnter(slider);
        }
    }

    @Override
    public void slideEnter(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] enterTarget = slider.getConfig().getPosition().getEnterTarget(child, dm.widthPixels, dm.heightPixels);
            Animator xEnter = ObjectAnimator.ofFloat(child, "translationX", enterTarget[0],enterTarget[2]);
            Animator yEnter = ObjectAnimator.ofFloat(child, "translationY", enterTarget[1],enterTarget[3]);
            listenerDialogFragment(slider, true, xEnter, yEnter);
        }
    }

    @Override
    public void slideExit(Slider slider) {
        View child = slider.getSlidableChild();
        if (child != null) {
            int[] exitTarget = slider.getConfig().getPosition().getExitTarget(slider, dm.widthPixels, dm.heightPixels);
            Animator xExit = ObjectAnimator.ofFloat(child, "translationX",  exitTarget[0],exitTarget[2]);
            Animator yExit = ObjectAnimator.ofFloat(child, "translationY", exitTarget[1],exitTarget[3]);
            listenerDialogFragment(slider, false, xExit, yExit);
        }
        slider.invalidate();
    }


    @Override
    public void slideExitAfter(Slider slider) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    private void attachToDialog(Slider slider) {
        ViewGroup root = (ViewGroup) dialog.getWindow().getDecorView();
        final ViewGroup decorChild = (ViewGroup) root.getChildAt(0);
        if (layoutParams == null) {
            layoutParams = (FrameLayout.LayoutParams) decorChild.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.CENTER;
        }
        decorChild.setLayoutParams(layoutParams);
        root.setBackgroundColor(Color.TRANSPARENT);
        root.removeView(decorChild);
        slider.addView(decorChild);
        root.addView(slider, 0);

        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (touchOutside && touchSlidableChild) {
                            dialog.dismiss();
                        }
                        touchSlidableChild = true;
                        break;
                }
                return false;
            }
        });

        decorChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchSlidableChild = false;
                        break;
                }
                return false;
            }
        });
    }

    private void listenerDialogFragment(final Slider slider, final boolean enter, Animator... animators) {
        int duration = activity.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        final SliderListener listener = slider.getConfig().getListener();

        animatorSet.setDuration(duration);
        animatorSet.playTogether(animators);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (enter) {
                    if (listener != null) {
                        listener.onSlideOpened();
                    }
                } else {
                    if (listener != null) {
                        listener.onSlideClosed();
                    }

                    if (slider.getConfig().isFinishUi()) {
                        slideExitAfter(slider);
                    }
                }
                animatorSet.removeListener(this);

            }
        });

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            valueAnimator.setInterpolator(animatorSet.getInterpolator());
        } else {
            valueAnimator.setInterpolator(new AccelerateInterpolator());
        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                if (listener != null) {
                    listener.onSlideChange(currentValue);
                }
            }
        });
        animatorSet.start();
        valueAnimator.start();
    }

}
