package com.komi.slider;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by Komi on 2016-01-21.
 */
public enum SliderPosition {
    LEFT {
        @Override
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return initialTouched&&edgeTouched;
        }

        @Override
        public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft,boolean hWrapped) {
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return contentViewLeft == 0;
        }

        @Override
        public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft,float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
            int endLeft =childLeft;
            int currentLeft=left-childLeft;
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
        public int getViewSize(float x, float y, int width, int height) {
            return getViewWidthOrHeight(width);
        }

        @Override
        public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
            return (childLeft == 0)?(x < edgeSize):(x > childLeft)&&(x<childLeft+ edgeSize);
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
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return LEFT.tryCaptureView(initialTouched, edgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft,boolean hWrapped) {
            return clamp(left, -maxWidth,childLeft);
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
        public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft,float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
            int endLeft = childLeft;
            int currentLeft=left-childLeft;
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
        public int getViewSize(float x, float y, int width, int height) {
            return LEFT.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
            return (childLeft == 0)?(x > edgeRange - edgeSize):(x < childLeft+edgeRange)&&(x>childLeft+edgeRange-edgeSize);
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
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return edgeTouched;
        }

        @Override
        public int clampViewPositionVertical(int maxHeight, int top, int childTop,boolean vWrapped) {
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return contentViewTop == 0;
        }

        @Override
        public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top,int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
            int endTop =childTop;
            int currentTop=top-childTop;
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
        public int getViewSize(float x, float y, int width, int height) {
            return getViewWidthOrHeight(height);
        }

        @Override
        public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
            return (childTop == 0)?(y < edgeSize):(y > childTop)&&(y<childTop+ edgeSize);
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
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return TOP.tryCaptureView(initialTouched, edgeTouched);
        }

        @Override
        public int clampViewPositionVertical(int maxHeight, int top, int childTop,boolean vWrapped) {
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return TOP.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top,int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
            int endTop = childTop;
            int currentTop=top-childTop;
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
        public int getViewSize(float x, float y, int width, int height) {
            return TOP.getViewSize(x, y, width, height);
        }

        @Override
        public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
            return (childTop == 0)?(y > edgeRange - edgeSize):(y < childTop+edgeRange)&&(y>childTop+edgeRange-edgeSize);

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

    HORIZONTAL {
        @Override
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return LEFT.tryCaptureView(initialTouched, edgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft,boolean hWrapped) {
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return LEFT.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft,float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
            int leftReleased = LEFT.onViewReleasedHorizontal(hWrapped, maxWidth, left, childLeft,xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
            int rightReleased = RIGHT.onViewReleasedHorizontal(hWrapped, maxWidth, left, childLeft,xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
            return leftReleased + rightReleased;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return LEFT.getViewSize(x, y, width, height);
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

    VERTICAL {
        @Override
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return TOP.tryCaptureView(initialTouched, edgeTouched);
        }

        @Override
        public int clampViewPositionVertical(int maxHeight, int top, int childTop,boolean vWrapped) {
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
        public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
            return TOP.onViewDragStateChanged(contentViewLeft, contentViewTop);
        }

        @Override
        public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top,int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
            int topReleased = TOP.onViewReleasedVertical(vWrapped, maxHeight, top,childTop, yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);
            int bottomReleased = BOTTOM.onViewReleasedVertical(vWrapped, maxHeight, top, childTop,yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);

            return topReleased + bottomReleased;
        }

        @Override
        public int getViewSize(float x, float y, int width, int height) {
            return TOP.getViewSize(x, y, width, height);
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

    ALL {
        private int width;
        private int height;

        @Override
        public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
            return VERTICAL.tryCaptureView(initialTouched, edgeTouched) || HORIZONTAL.tryCaptureView(initialTouched, edgeTouched);
        }

        @Override
        public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft,boolean hWrapped) {
            if (getSlidingPosition() == HORIZONTAL || !isEdgeOnly()) {
                return HORIZONTAL.clampViewPositionHorizontal(maxWidth, left, childLeft,hWrapped);
            } else {
                return super.clampViewPositionHorizontal(maxWidth, left, childLeft,hWrapped);
            }
        }

        @Override
        public int clampViewPositionVertical(int maxHeight, int top, int childTop,boolean vWrapped) {
            if (getSlidingPosition() == VERTICAL || !isEdgeOnly()) {
                return VERTICAL.clampViewPositionVertical(maxHeight, top,childTop, vWrapped);
            } else {
                return super.clampViewPositionVertical(maxHeight, top,childTop,vWrapped);
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
        public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft,float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
            return HORIZONTAL.onViewReleasedHorizontal(hWrapped, maxWidth, left,childLeft, xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
        }

        @Override
        public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top,int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
            return VERTICAL.onViewReleasedVertical(vWrapped, maxHeight, top, childTop,yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);
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
     * @return 当position为如下情况时, 判断当前的滑动方向
     * @see #ALL
     * @see #HORIZONTAL
     * @see #VERTICAL
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

    public boolean tryCaptureView(boolean initialTouched, boolean edgeTouched) {
        return false;
    }


    public int clampViewPositionHorizontal(int maxWidth, int left, int childLeft,boolean hWrapped) {
        return childLeft;
    }

    /**
     *
     * @param maxHeight 最大值
     * @param top       sliderChild移动时的top
     * @param childTop  sliderChild初始状态的top
     * @param vWrapped  child的高度宽度是否被包裹在slider
     * @return          child可移动的高度值
     */
    public int clampViewPositionVertical(int maxHeight, int top, int childTop,boolean vWrapped) {
        return childTop;
    }

    public int getViewHorizontalDragRange(int contentViewWidth) {
        return 0;
    }

    public int getViewVerticalDragRange(int contentViewHeight) {
        return 0;
    }


    /**
     * 获取滑动松开手后，获取横向坐标状态的判断
     *
     * @param hWrapped                sliderChild的宽度是否被包裹在slider
     * @param maxWidth                slider的宽度
     * @param left                    sliderChild松手时的left
     * @param childLeft               sliderChild初始状态的left
     * @param xvel                    x轴的滑动速度
     * @param hThreshold              横向划开的宽度的阀值
     * @param hOverVelocityThreshold  是否达到设定的横向划开速度
     * @param velocityThreshold       横向划开速度阀值
     * @return                        松开手后sliderChild要移动到终点的Left位置
     */
    public int onViewReleasedHorizontal(boolean hWrapped, int maxWidth, int left, int childLeft,float xvel, int hThreshold, boolean hOverVelocityThreshold, float velocityThreshold) {
        return childLeft;
    }

    /**
     * 获取滑动松开手后，获取纵向坐标状态的判断
     *
     * @param vWrapped                 sliderChild的高度是否被包裹在slider
     * @param maxHeight                slider的宽度
     * @param top                      sliderChild松手时的top
     * @param childTop                 sliderChild初始状态的top
     * @param yvel                     y轴的滑动速度
     * @param vThreshold               纵向划开的高度的阀值
     * @param vOverVelocityThreshold   是否达到设定的纵向划开速度
     * @param velocityThreshold        纵向划开速度阀值
     * @return                         松开手后sliderChild要移动到终点的top位置
     */
    public int onViewReleasedVertical(boolean vWrapped, int maxHeight, int top,int childTop, float yvel, int vThreshold, boolean vOverVelocityThreshold, float velocityThreshold) {
        return childTop;
    }


    public float onViewPositionChanged(int contentViewWidth, int contentViewHeight, int left, int top) {
        return 0;
    }

    public boolean onViewDragStateChanged(int contentViewLeft, int contentViewTop) {
        return false;
    }

    /**
     * @return 获取当前position对应的Flags
     */
    public int getEdgeFlags() {
        return 0;
    }

    protected final int getViewWidthOrHeight(int size) {
        return size;
    }

    /**
     * 根据来返回可划起的SliderChild的范围
     * @see SliderPosition
     *
     * @param x         x落点坐标值
     * @param y         y落点坐标值
     * @param width     可触摸宽度范围
     * @param height    可触摸高度范围
     * @return          设定的可触摸划起sliderChild长或宽的大小
     */
    public int getViewSize(float x, float y, int width, int height) {
        return 0;
    }


    /**
     *
     * @param childLeft     sliderChild的初始left
     * @param childTop      sliderChild的初始top
     * @param x             当前触摸点的x坐标
     * @param y             当前触摸点的x坐标
     * @param edgeRange     可触摸划起sliderChild的范围
     * @param edgeSize      设定的可触摸划起sliderChild长或宽的大小     
     * @return              当前方向是否可滑动
     */
    public boolean canDragFromEdge(int childLeft, int childTop, float x, float y, float edgeRange, float edgeSize) {
        return false;
    }


    /**
     * 设置背景阴影区域
     * @param child sliderChild
     * @param rect  阴影区域
     */
    public void setScrimRect(View child, Rect rect) {
    }

    /**
     * @return 当ui为activity时，获取当前方向的动画
     */
    public int[] getActivitySlidingAmins() {
        return new int[]{android.R.anim.fade_in, android.R.anim.fade_out};
    }

    
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
        return Math.max(Math.min(1, 1f - (Math.abs(progress) / range)), 0);
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

    public static SliderPosition getSliderPosition(int edgeFlag) {
        for (SliderPosition position : SliderPosition.values()) {
            if (position.getEdgeFlags() == edgeFlag) {
                return position;
            }
        }
        return LEFT;
    }
}
