package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

import com.komi.slider.R;
import com.komi.slider.ViewDragHelper;

/**
 * Created by Komi on 2016/2/20.
 */
public class VerticalPosition extends SliderPosition {
    private static VerticalPosition ourInstance = new VerticalPosition();

    public static VerticalPosition getInstance() {
        return ourInstance;
    }

    private VerticalPosition() {
    }

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return TOP.tryCaptureView(initialTouched, edgeTouched) || BOTTOM.tryCaptureView(initialTouched, edgeTouched);
    }

    @Override
    public int clampViewPositionVertical(int maxHeight, int top, int childTop, boolean vWrapped) {
        return clamp(top, -maxHeight, maxHeight);
    }

    @Override
    public int getEdgeFlags() {
        return ViewDragHelper.EDGE_TOP | ViewDragHelper.EDGE_BOTTOM;
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
        return TOP.onViewDragStateChanged(left, top, childLeft, childTop);
    }

    @Override
    public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top, int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
        int topReleased = TOP.onViewReleasedVertical(vWrapped, maxHeight, top, childTop, yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);
        int bottomReleased = BOTTOM.onViewReleasedVertical(vWrapped, maxHeight, top, childTop, yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);
        boolean isBottomReleased = bottomReleased != childTop;
        return isBottomReleased?bottomReleased:topReleased;
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        return TOP.getViewSize(x, y, width, height,childLeft,childTop);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        boolean dragTop = TOP.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);
        boolean dragBottom = BOTTOM.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);
        return dragTop || dragBottom;

    }

    @Override
    public int[] getActivitySlidingAmins() {
        return new int[]{R.anim.activity_top_in, R.anim.activity_bottom_out};
    }

    @Override
    public void setScrimRect(View child,int childLeft,int childTop, Rect rect) {
        SliderPosition position = getTouchPosition(child.getLeft(), childLeft,child.getTop(),childTop);
        if (position != null) {
            position.setScrimRect(child, childLeft,childTop,rect);
        }
    }

    @Override
    public Rect getSlidingInRect(View decorView) {
        return TOP.getSlidingInRect(decorView);
    }

    @Override
    public Rect getSlidingOutRect(View decorView) {
        return BOTTOM.getSlidingOutRect(decorView);
    }
}
