package com.komi.slider;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by Komi on 2016-01-21.
 */
public enum SliderPosition {
    LEFT {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return hEdgeTouched;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return clamp(left, 0, child.getWidth());
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return contentViewLeft == 0;
        }

        @Override
        public int onViewReleasedHorizontal(int childWidth, int left, float xvel, int hThreshold, boolean hSwiping, float velocityThreshold) {
            int settleLeft = 0;
            if (xvel > 0) {
                if (Math.abs(xvel) > velocityThreshold && !hSwiping) {
                    settleLeft = childWidth;
                } else if (left > hThreshold) {
                    settleLeft = childWidth;
                }
            } else if (xvel == 0 && left > hThreshold) {
                settleLeft = childWidth;
            }


            return settleLeft;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return getViewWidthOrHeight(width);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            return x < edgeSize;
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }

        @Override
        public int[] getActivitySlidingAmins() {
            return new int[]{R.anim.activity_left_in, R.anim.activity_left_out};
        }

        @Override
        public Rect getSlidingInRect(View decorView) {
            return new Rect(decorView.getWidth(), decorView.getTop(), decorView.getLeft(), decorView.getTop());
        }

        @Override
        public Rect getSlidingOutRect(View decorView) {
            return new Rect(decorView.getLeft(), decorView.getTop(), decorView.getWidth(), decorView.getTop());
        }
    },

    RIGHT {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return LEFT.tryCaptureView(hEdgeTouched, vEdgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return clamp(left, -child.getWidth(), 0);
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return LEFT.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedHorizontal(int childWidth, int left, float xvel, int hThreshold, boolean hSwiping, float velocityThreshold) {
            int settleLeft = 0;
            if (xvel < 0) {
                if (Math.abs(xvel) > velocityThreshold && !hSwiping) {
                    settleLeft = -childWidth;
                } else if (left < -hThreshold) {
                    settleLeft = -childWidth;
                }

            } else if (xvel == 0 && left < -hThreshold) {
                settleLeft = -childWidth;
            }
            return settleLeft;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return LEFT.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            return x > edgeRange - edgeSize;
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }

        @Override
        public int[] getActivitySlidingAmins() {
            return new int[]{R.anim.activity_right_in, R.anim.activity_right_out};
        }

        @Override
        public Rect getSlidingInRect(View decorView) {
            return new Rect(-decorView.getWidth(), decorView.getTop(), decorView.getLeft(), decorView.getTop());
        }

        @Override
        public Rect getSlidingOutRect(View decorView) {
            return new Rect(decorView.getLeft(), decorView.getTop(), -decorView.getWidth(), decorView.getTop());
        }
    },
    TOP {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return vEdgeTouched;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return clamp(top, 0, child.getHeight());

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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return contentViewTop == 0;
        }

        @Override
        public int onViewReleasedVertical(int childHeight, int top, float yvel, int vThreshold, boolean vSwiping, float velocityThreshold) {
            int settleTop = 0;
            if (yvel > 0) {
                if (Math.abs(yvel) > velocityThreshold && !vSwiping) {
                    settleTop = childHeight;
                } else if (top > vThreshold) {
                    settleTop = childHeight;
                }
            } else if (yvel == 0 && top > vThreshold) {
                settleTop = childHeight;
            }

            return settleTop;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return getViewWidthOrHeight(height);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            return y < edgeSize;
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            rect.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }

        @Override
        public int[] getActivitySlidingAmins() {
            return new int[]{R.anim.activity_top_in, R.anim.activity_top_out};
        }

        @Override
        public Rect getSlidingInRect(View decorView) {
            return new Rect(decorView.getLeft(), decorView.getHeight(), decorView.getLeft(), decorView.getTop());
        }

        @Override
        public Rect getSlidingOutRect(View decorView) {
            return new Rect(decorView.getLeft(), decorView.getTop(), decorView.getLeft(), decorView.getHeight());
        }
    },
    BOTTOM {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return TOP.tryCaptureView(hEdgeTouched, vEdgeTouched);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return clamp(top, -child.getHeight(), 0);
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return TOP.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedVertical(int childHeight, int top, float yvel, int vThreshold, boolean vSwiping, float velocityThreshold) {
            int settleTop = 0;
            if (yvel < 0) {
                if (Math.abs(yvel) > velocityThreshold && !vSwiping) {
                    settleTop = -childHeight;
                } else if (top < -vThreshold) {
                    settleTop = -childHeight;
                }
            } else if (yvel == 0 && top < -vThreshold) {
                settleTop = -childHeight;
            }
            return settleTop;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return TOP.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            return y > edgeRange - edgeSize;
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
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
    },
    VERTICAL {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return TOP.tryCaptureView(hEdgeTouched, vEdgeTouched);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return clamp(top, -child.getHeight(), child.getHeight());
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return TOP.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedVertical(int childHeight, int top, float yvel, int vThreshold, boolean vSwiping, float velocityThreshold) {
            int topReleased = TOP.onViewReleasedVertical(childHeight, top, yvel, vThreshold, vSwiping, velocityThreshold);
            int bottomReleased = BOTTOM.onViewReleasedVertical(childHeight, top, yvel, vThreshold, vSwiping, velocityThreshold);

            return topReleased + bottomReleased;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return TOP.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            boolean dragTop = TOP.canDragFromEdge(x, y, edgeRange, edgeSize);
            boolean dragBottom = BOTTOM.canDragFromEdge(x, y, edgeRange, edgeSize);
            return dragTop || dragBottom;

        }

        @Override
        public int[] getActivitySlidingAmins() {
            return new int[]{R.anim.activity_top_in, R.anim.activity_bottom_out};
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            SliderPosition position = getTouchPosition(child.getLeft(), child.getTop());
            if (position != null) {
                position.setScrimRect(child, rect);
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
    },
    HORIZONTAL {
        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return LEFT.tryCaptureView(hEdgeTouched, vEdgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return clamp(left, -child.getWidth(), child.getWidth());
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return LEFT.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedHorizontal(int childWidth, int left, float xvel, int hThreshold, boolean hSwiping, float velocityThreshold) {
            int leftReleased = LEFT.onViewReleasedHorizontal(childWidth, left, xvel, hThreshold, hSwiping, velocityThreshold);
            int rightReleased = RIGHT.onViewReleasedHorizontal(childWidth, left, xvel, hThreshold, hSwiping, velocityThreshold);
            return leftReleased + rightReleased;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return LEFT.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            boolean dragLeft = LEFT.canDragFromEdge(x, y, edgeRange, edgeSize);
            boolean dragRight = RIGHT.canDragFromEdge(x, y, edgeRange, edgeSize);
            return dragLeft || dragRight;
        }

        @Override
        public int[] getActivitySlidingAmins() {
            return new int[]{R.anim.activity_left_in, R.anim.activity_right_out};
        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            SliderPosition position = getTouchPosition(child.getLeft(), child.getTop());
            if (position != null) {
                position.setScrimRect(child, rect);
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
    },

    ALL {
        private int width;
        private int height;

        @Override
        public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
            return VERTICAL.tryCaptureView(hEdgeTouched, vEdgeTouched) || HORIZONTAL.tryCaptureView(hEdgeTouched, vEdgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (getSlidingPosition() == HORIZONTAL || !isEdgeOnly()) {
                return HORIZONTAL.clampViewPositionHorizontal(child, left, dx);
            } else {
                return super.clampViewPositionHorizontal(child, left, dx);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (getSlidingPosition() == VERTICAL || !isEdgeOnly()) {
                return VERTICAL.clampViewPositionVertical(child, top, dy);
            } else {
                return super.clampViewPositionVertical(child, top, dy);
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return VERTICAL.onViewDragStateChanged(contentViewLeft, contentViewTop) && HORIZONTAL.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedHorizontal(int childWidth, int left, float xvel, int hThreshold, boolean hSwiping, float velocityThreshold) {
            return HORIZONTAL.onViewReleasedHorizontal(childWidth, left, xvel, hThreshold, hSwiping, velocityThreshold);
        }

        @Override
        public int onViewReleasedVertical(int childHeight, int top, float yvel, int vThreshold, boolean vSwiping, float velocityThreshold) {
            return VERTICAL.onViewReleasedVertical(childHeight, top, yvel, vThreshold, vSwiping, velocityThreshold);
        }

        @Override
        public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
            boolean canDragHorizontal = HORIZONTAL.canDragFromEdge(x, y, edgeRange, edgeSize);
            boolean canDragVertical = VERTICAL.canDragFromEdge(x, y, edgeRange, edgeSize);
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
        public int getViewSize(float x, float y, int width, int height) {
            float xDistance = Math.min(Math.abs(x - width), Math.abs(x - 0));
            float yDistance = Math.min(Math.abs(y - height), Math.abs(y - 0));
            this.width = width;
            this.height = height;
            int size = xDistance < yDistance ? width : height;
            return size;

        }

        @Override
        public void setScrimRect(View child, Rect rect) {
            SliderPosition position = getTouchPosition(child.getLeft(), child.getTop());
            if (isEdgeOnly()) {
                if (position != null) {
                    position.setScrimRect(child, rect);
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
    };


    private SliderPosition slidingPosition;

    private boolean edgeOnly;

    /**
     * 当position为
     *
     * @return
     * @see #ALL
     * @see #HORIZONTAL
     * @see #VERTICAL
     * 时，当前滑动的方向判断
     */
    public SliderPosition getSlidingPosition() {
        return slidingPosition;
    }

    public void setSlidingPosition(SliderPosition slidingPosition) {
        this.slidingPosition = slidingPosition;
    }

    public boolean isEdgeOnly() {
        return edgeOnly;
    }

    public void setEdgeOnly(boolean edgeOnly) {
        this.edgeOnly = edgeOnly;
    }

    public boolean tryCaptureView(boolean hEdgeTouched, boolean vEdgeTouched) {
        return false;
    }


    public int clampViewPositionHorizontal(View child, int left, int dx) {
        return 0;
    }

    public int clampViewPositionVertical(View child, int top, int dy) {
        return 0;
    }

    public int getViewHorizontalDragRange(int contentViewWidth) {
        return 0;
    }

    public int getViewVerticalDragRange(int contentViewHeight) {
        return 0;
    }


    public int onViewReleasedHorizontal(int childWidth, int left, float xvel, int hThreshold, boolean hSwiping, float velocityThreshold) {
        return 0;
    }

    public int onViewReleasedVertical(int childHeight, int top, float yvel, int vThreshold, boolean vSwiping, float velocityThreshold) {
        return 0;
    }


    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return 0;
    }

    public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
        return false;
    }

    /**
     * 获取当前position对应的Flags
     *
     * @return
     */
    public int getEdgeFlags() {
        return 0;
    }

    protected final int getViewWidthOrHeight(int size) {
        return size;
    }

    public int getViewSize(float x, float y, int width, int height) {
        return 0;
    }

    /**
     * 判断是否当前方向可滑动
     *
     * @param x
     * @param y
     * @param edgeRange
     * @param edgeSize
     * @return
     */
    public boolean canDragFromEdge(float x, float y, float edgeRange, float edgeSize) {
        return false;
    }

    /**
     * 设置背景阴影区域
     */
    public void setScrimRect(View child, Rect rect) {
    }

    /**
     * 当ui为activity时，获取当前方向的动画
     *
     * @return
     */
    public int[] getActivitySlidingAmins() {
        return new int[]{android.R.anim.fade_in, android.R.anim.fade_out};
    }


    //int startLeft, int startTop, int finalLeft, int finalTop
    public Rect getSlidingInRect(View decorView) {
        return null;
    }

    public Rect getSlidingOutRect(View decorView) {
        return null;
    }


    /**
     * Clamp Integer values to a given range
     *
     * @param value the value to clamp
     * @param min   the minimum value
     * @param max   the maximum value
     * @return the clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float percent(float progress, float range) {
        return 1f - (Math.abs(progress) / range);
    }

    public static SliderPosition getTouchPosition(int left, int top) {
        SliderPosition position = null;
        if (left != 0 && top == 0) {
            position = left > 0 ? LEFT : RIGHT;
        } else if (top != 0 && left == 0) {
            position = top > 0 ? TOP : BOTTOM;
        }
        return position;
    }
}
