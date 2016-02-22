package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class HorizontalPosition extends SliderPosition {
    private static HorizontalPosition ourInstance = new HorizontalPosition();

    public static HorizontalPosition getInstance() {
        return ourInstance;
    }

    private HorizontalPosition() {
    }

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return LEFT.tryCaptureView(initialTouched, edgeTouched) || RIGHT.tryCaptureView(initialTouched, edgeTouched);
    }

    @Override
    public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft, boolean hWrapped) {
        return clamp(left, -maxWidth, maxWidth);
    }

    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT;
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
        return LEFT.onViewDragStateChanged(left,top,childLeft,childTop);
    }

    @Override
    public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft, float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
        int leftReleased = LEFT.onViewReleasedHorizontal(hWrapped, maxWidth, left, childLeft, xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
        int rightReleased = RIGHT.onViewReleasedHorizontal(hWrapped, maxWidth, left, childLeft, xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
        boolean isRightReleased = rightReleased != childLeft;
        return isRightReleased?rightReleased:leftReleased;
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return LEFT.getViewSize(x, y, width, height,childLeft,childTop);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        boolean dragLeft = LEFT.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);
        boolean dragRight = RIGHT.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);
        return dragLeft || dragRight;
    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_left_in, R.anim.activity_right_out};
    }

    @Override
    public void setScrimRect(View child,int childLeft,int childTop, Rect rect) {
        SliderPosition position = getTouchPosition(child.getLeft(), childLeft,child.getTop(),childTop);
        if (position != null) {
            position.setScrimRect(child,childLeft,childTop,rect);
        }
    }

    @Override
    public Rect getSlidingInRect(View decorView) {
        return LEFT.getSlidingInRect(decorView);
    }

    @Override
    public Rect getSlidingOutRect(View decorView) {
        return RIGHT.getSlidingOutRect(decorView);
    }
}
