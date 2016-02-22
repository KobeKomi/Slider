package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class BottomPosition extends SliderPosition {
    private static BottomPosition ourInstance = new BottomPosition();

    public static BottomPosition getInstance() {
        return ourInstance;
    }

    private BottomPosition() {
    }

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return TOP.tryCaptureView(initialTouched, edgeTouched);
    }

    @Override
    public int clampViewPositionVertical(int maxHeight, int top, int childTop, boolean vWrapped) {
        return clamp(top, -maxHeight, childTop);
    }

    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_BOTTOM;
    }

    @Override
    public int getViewVerticalDragRange(int contentViewHeight) {
        return TOP.getViewVerticalDragRange(contentViewHeight);
    }

    @Override
    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return TOP.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
    }

    @Override
    public boolean onViewDragStateChanged(int left, int top,int childLeft,int childTop) {
        return TOP.onViewDragStateChanged(left,top,childLeft,childTop);
    }

    @Override
    public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top, int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
        int endTop = childTop;
        int currentTop = top - childTop;
        if (yvel < 0) {
            if (Math.abs(yvel) > velocityThreshold && !vOverVelocityThreshold) {
                endTop = -maxHeight;
            } else if (currentTop < -vThreshold) {
                endTop = -maxHeight;
            }
        } else if (yvel == 0 && currentTop < -vThreshold) {
            endTop = -maxHeight;
        }
        return endTop;
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return TOP.getViewSize(x, y, width, height,childLeft,childTop);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        return (childTop == 0) ? (y > edgeRange - edgeSize) : (y < childTop + edgeRange) && (y > childTop + edgeRange - edgeSize);

    }

    @Override
    public void setScrimRect(View child,int childLeft,int childTop, Rect rect) {
        rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_bottom_in, R.anim.activity_bottom_out};
    }

    @Override
    public Rect getSlidingInRect(View decorView) {
        return new Rect(decorView.getLeft(), -decorView.getHeight(), decorView.getLeft(), decorView.getTop());
    }

    @Override
    public Rect getSlidingOutRect(View decorView) {
        return new Rect(decorView.getLeft(), decorView.getTop(), decorView.getLeft(), -decorView.getHeight());
    }
}
