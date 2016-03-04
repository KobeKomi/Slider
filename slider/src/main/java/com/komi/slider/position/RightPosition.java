package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class RightPosition extends SliderPosition {
    private static RightPosition ourInstance = new RightPosition();

    public static RightPosition getInstance() {
        return ourInstance;
    }

    private RightPosition() {
    }

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return LEFT.tryCaptureView(initialTouched, edgeTouched);
    }

    @Override
    public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft, boolean hWrapped) {
        return clamp(left, -maxWidth, childLeft);
    }


    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_RIGHT;
    }

    @Override
    public int getViewHorizontalDragRange(int contentViewWidth) {
        return LEFT.getViewHorizontalDragRange(contentViewWidth);
    }

    @Override
    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return LEFT.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
    }

    @Override
    public boolean onViewDragStateChanged(int left, int top,int childLeft,int childTop) {
        return LEFT.onViewDragStateChanged(left,top,childLeft, childTop);
    }

    @Override
    public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft, float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
        int endLeft = childLeft;
        int currentLeft = left - childLeft;
        if (xvel < 0) {
            if (Math.abs(xvel) > velocityThreshold && !hOverVelocityThreshold) {
                endLeft = -maxWidth;
            } else if (currentLeft < -hThreshold) {
                endLeft = -maxWidth;
            }

        } else if (xvel == 0 && currentLeft < -hThreshold) {
            endLeft = -maxWidth;
        }
        return endLeft;
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return LEFT.getViewSize(x, y, width, height,childLeft,childTop);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        return (childLeft == 0) ? (x > edgeRange - edgeSize) : (x < childLeft + edgeRange) && (x > childLeft + edgeRange - edgeSize);
    }

    @Override
    public void setScrimRect(View child,int childLeft,int childTop, Rect rect) {
        rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_right_in, R.anim.activity_right_out};
    }

    @Override
    public int[] getEnterTarget(View childView,int maxWidth,int maxHeight) {
        int []enterTarget={-maxWidth, childView.getTop(),childView.getLeft(),childView.getTop()};
        return enterTarget;
    }

    @Override
    public int[] getExitTarget(View childView,int maxWidth,int maxHeight) {
        int []exitTarget={childView.getLeft(),childView.getTop(),-maxWidth, childView.getTop()};
        return exitTarget;
    }
}
