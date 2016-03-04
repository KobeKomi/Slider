package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class TopPosition extends SliderPosition {
    private static TopPosition ourInstance = new TopPosition();

    public static TopPosition getInstance() {
        return ourInstance;
    }

    private TopPosition() {
    }

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return edgeTouched;
    }

    @Override
    public int clampViewPositionVertical(int maxHeight, int top, int childTop, boolean vWrapped) {
        return clamp(top, childTop, maxHeight);
    }

    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_TOP;
    }

    @Override
    public int getViewVerticalDragRange(int contentViewHeight) {
        return contentViewHeight;
    }

    @Override
    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return percent((float) top, (float) contentViewHeight);
    }

    @Override
    public boolean onViewDragStateChanged(int left, int top,int childLeft,int childTop) {
        return top == childTop;
    }

    @Override
    public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top, int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
        int endTop = childTop;
        int currentTop = top - childTop;
        if (yvel > 0) {
            if (Math.abs(yvel) > velocityThreshold && !vOverVelocityThreshold) {
                endTop = maxHeight;
            } else if (currentTop > vThreshold) {
                endTop = maxHeight;
            }
        } else if (yvel == 0 && currentTop > vThreshold) {
            endTop = maxHeight;
        }

        return endTop;
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return getViewWidthOrHeight(height);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        return (childTop == 0) ? (y < edgeSize) : (y > childTop) && (y < childTop + edgeSize);
    }

    @Override
    public void setScrimRect(View child, int childLeft, int childTop, Rect rect) {
        rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_top_in, R.anim.activity_top_out};
    }

    @Override
    public int[] getEnterTarget(View childView,int maxWidth,int maxHeight) {
        int []enterTarget={childView.getLeft(), maxHeight,childView.getLeft(),childView.getTop()};
        return enterTarget;
    }

    @Override
    public int[] getExitTarget(View childView,int maxWidth,int maxHeight) {
        int []exitTarget={childView.getLeft(),childView.getTop(),childView.getLeft(), maxHeight};
        return exitTarget;
    }
}
