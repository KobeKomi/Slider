package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class LeftPosition extends SliderPosition {
    private static LeftPosition ourInstance = new LeftPosition();

    public static LeftPosition getInstance() {
        return ourInstance;
    }

    private LeftPosition() {
    }


    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return initialTouched && edgeTouched;
    }

    @Override
    public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft, boolean hWrapped) {
        return clamp(left, childLeft, maxWidth);
    }

    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_LEFT;
    }

    @Override
    public int getViewHorizontalDragRange(int contentViewWidth) {
        return contentViewWidth;
    }

    @Override
    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return percent((float) left, (float) contentViewWidth);
    }

    @Override
    public boolean onViewDragStateChanged(int left, int top,int childLeft,int childTop) {
        return left == childLeft;
    }

    @Override
    public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft, float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
        int endLeft = childLeft;
        int currentLeft = left - childLeft;
        if (xvel > 0) {
            if (Math.abs(xvel) > velocityThreshold && !hOverVelocityThreshold) {
                endLeft = maxWidth;
            } else if (currentLeft > hThreshold) {
                endLeft = maxWidth;
            }
        } else if (xvel == 0 && currentLeft > hThreshold) {
            endLeft = maxWidth;
        }


        return endLeft;
    }


    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return getViewWidthOrHeight(width);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        return (childLeft == 0) ? (x < edgeSize) : (x > childLeft) && (x < childLeft + edgeSize);
    }

    @Override
    public void setScrimRect(View child, int childLeft, int childTop, Rect rect) {
        rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_left_in, R.anim.activity_left_out};
    }

    @Override
    public int[] getEnterTarget(View childView,int maxWidth,int maxHeight) {
        int []enterTarget={maxWidth, childView.getTop(),childView.getLeft(),childView.getTop()};
        return enterTarget;
    }

    @Override
    public int[] getExitTarget(View childView,int maxWidth,int maxHeight) {
        int []exitTarget={childView.getLeft(),childView.getTop(),maxWidth, childView.getTop()};
        return exitTarget;
    }
}
