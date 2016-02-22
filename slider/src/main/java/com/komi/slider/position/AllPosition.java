package com.komi.slider.position;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by Komi on 2016/2/20.
 */
public class AllPosition extends SliderPosition {
    private static AllPosition ourInstance = new AllPosition();

    public static AllPosition getInstance() {
        return ourInstance;
    }

    private AllPosition() {
    }

    private int width;
    private int height;

    @Override
    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return VERTICAL.tryCaptureView(initialTouched, edgeTouched) || HORIZONTAL.tryCaptureView(initialTouched, edgeTouched);
    }

    @Override
    public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft, boolean hWrapped) {
        if (getSlidingPosition() == HORIZONTAL || !isEdgeOnly()) {
            return HORIZONTAL.clampViewPositionHorizontal(maxWidth, left, childLeft, hWrapped);
        } else {
            return super.clampViewPositionHorizontal(maxWidth, left, childLeft, hWrapped);
        }
    }

    @Override
    public int clampViewPositionVertical(int maxHeight, int top, int childTop, boolean vWrapped) {
        if (getSlidingPosition() == VERTICAL || !isEdgeOnly()) {
            return VERTICAL.clampViewPositionVertical(maxHeight, top, childTop, vWrapped);
        } else {
            return super.clampViewPositionVertical(maxHeight, top, childTop, vWrapped);
        }
    }

    @Override
    public int getEdgeFlags() {
        return VERTICAL.getEdgeFlags() | HORIZONTAL.getEdgeFlags();
    }

    @Override
    public int getViewHorizontalDragRange(int contentViewWidth) {
        return HORIZONTAL.getViewHorizontalDragRange(contentViewWidth);
    }

    @Override
    public int getViewVerticalDragRange(int contentViewHeight) {
        return VERTICAL.getViewVerticalDragRange(contentViewHeight);
    }

    @Override
    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        SliderPosition position = getSlidingPosition();
        if (isEdgeOnly()) {
            if (position != null) {
                return position.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
            } else {
                float vPercent = VERTICAL.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
                float hPercent = HORIZONTAL.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
                return vPercent * hPercent;
            }
        } else {
            float vPercent = VERTICAL.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
            float hPercent = HORIZONTAL.onViewPositionChanged(contentViewWidth, contentViewHeight, left, top);
            return vPercent * hPercent;
        }

    }

    @Override
    public boolean onViewDragStateChanged(int left, int top,int childLeft,int childTop) {
        return  VERTICAL.onViewDragStateChanged(left,top,childLeft,childTop)&&HORIZONTAL.onViewDragStateChanged(left,top,childLeft,childTop);
    }

    @Override
    public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft, float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
        return HORIZONTAL.onViewReleasedHorizontal(hWrapped, maxWidth, left, childLeft, xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
    }

    @Override
    public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top, int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
        return VERTICAL.onViewReleasedVertical(vWrapped, maxHeight, top, childTop, yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);
    }

    @Override
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        boolean canDragHorizontal = HORIZONTAL.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);
        boolean canDragVertical = VERTICAL.canDragFromEdge(childLeft, childTop, x, y, edgeRange, edgeSize);

        if (canDragHorizontal && edgeRange == width) {
            setSlidingPosition(HORIZONTAL);
            return canDragHorizontal;
        } else if (canDragVertical && edgeRange == height) {
            setSlidingPosition(VERTICAL);
            return canDragVertical;
        } else {
            setSlidingPosition(null);
            return canDragHorizontal || canDragVertical;
        }
    }

    @Override
    public int getViewSize(float x, float y, int width, int height,int childLeft,int childTop) {
        this.width = width;
        this.height = height;
        float xDistance = Math.min(Math.abs(x - width), Math.abs(x - childLeft));
        float yDistance = Math.min(Math.abs(y - height), Math.abs(y - childTop));
        int size = xDistance < yDistance ? width : height;
        return size;

    }

    @Override
    public void setScrimRect(View child,int childLeft,int childTop, Rect rect) {
        SliderPosition position = getTouchPosition(child.getLeft(), childLeft,child.getTop(),childTop);
        if (isEdgeOnly()) {
            if (position != null) {
                position.setScrimRect(child, childLeft,childTop,rect);
            } else {
                rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            }
        } else {
            rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }

    }

    @Override
    public Rect getSlidingInRect(View decorView) {
        return new Rect(-decorView.getWidth(), decorView.getHeight(), decorView.getLeft(), decorView.getTop());

    }

    @Override
    public Rect getSlidingOutRect(View decorView) {
        return new Rect(decorView.getLeft(), decorView.getTop(), -decorView.getWidth(), decorView.getHeight());

    }
}
