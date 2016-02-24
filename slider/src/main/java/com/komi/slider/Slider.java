/*
 * Copyright (c) 2014. 52inc
 * All Rights Reserved.
 */

package com.komi.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.komi.slider.position.SliderPosition;


/**
 * Created by Komi on 2016-01-21.
 */
public class Slider extends FrameLayout {

    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    //拉开的背景
    private Rect scrimRect = new Rect();
    private float scrimAlpha;
    private float mScrollPercent;

    private View mSlidableChild;

    //slide布局时的坐标
    private int slidableChildLeft;
    private int slidableChildTop;

    private ViewDragHelper mDragHelper;

    private SliderListener mListener;
    private boolean mIsLocked = false;
    private boolean mIsEdgeTouched = false;

    private SliderConfig mConfig;

    private boolean fastSlidingTag;

    public Slider(Context context) {
        super(context);
        mConfig = new SliderConfig.Builder().build();
    }

    public Slider(Context context, SliderConfig config) {
        super(context);
        mConfig = (config == null ? new SliderConfig.Builder().build() : config);
        initDate();
    }

    //通过xml初始化
    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SliderStyle);
        int edgeFlag = a.getInt(R.styleable.SliderStyle_position, SliderPosition.LEFT.getEdgeFlags());
        boolean edgeOnly = a.getBoolean(R.styleable.SliderStyle_edgeOnly, false);
        float edgePercent = a.getFloat(R.styleable.SliderStyle_edgePercent, SliderConfig.DEFAULT_EDGE_PERCENT);
        int scrimColor = a.getColor(R.styleable.SliderStyle_scrimColor, SliderConfig.DEFAULT_SCRIM_COLOR);
        float scrimStartAlpha = a.getFloat(R.styleable.SliderStyle_scrimStartAlpha, SliderConfig.DEFAULT_SCRIM_START_ALPHA);
        float scrimEndAlpha = a.getFloat(R.styleable.SliderStyle_scrimEndAlpha, SliderConfig.DEFAULT_SCRIM_END_ALPHA);
        float velocityThreshold = a.getFloat(R.styleable.SliderStyle_velocityThreshold, SliderConfig.DEFAULT_VELOCITY_THRESHOLD);
        float distanceThresholdPercent = a.getFloat(R.styleable.SliderStyle_distanceThresholdPercent, SliderConfig.DEFAULT_DISTANCE_THRESHOLD_PERCENT);
        boolean slideEnter = a.getBoolean(R.styleable.SliderStyle_slideEnter, true);
        boolean slideExit = a.getBoolean(R.styleable.SliderStyle_slideExit, true);
        float sensitivity = a.getFloat(R.styleable.SliderStyle_sensitivity, SliderConfig.DEFAULT_SENSITIVITY);
        int primaryColor = a.getColor(R.styleable.SliderStyle_primaryColor, SliderConfig.DEFAULT_PRIMARY_COLOR);
        int secondaryColor = a.getColor(R.styleable.SliderStyle_secondaryColor, SliderConfig.DEFAULT_SECONDARY_COLOR);

        a.recycle();
        mConfig = new SliderConfig.Builder()
                .position(SliderPosition.getSliderPosition(edgeFlag))
                .edgeOnly(edgeOnly)
                .edgePercent(edgePercent)
                .scrimColor(scrimColor)
                .scrimStartAlpha(scrimStartAlpha)
                .scrimEndAlpha(scrimEndAlpha)
                .velocityThreshold(velocityThreshold)
                .distanceThreshold(distanceThresholdPercent)
                .slideEnter(slideEnter)
                .slideExit(slideExit)
                .sensitivity(sensitivity)
                .primaryColor(primaryColor)
                .secondaryColor(secondaryColor)
                .build();
        initDate();
    }


    public void setConfig(SliderConfig config) {
        this.mConfig = config;
        mConfig.getPosition().setEdgeOnly(mConfig.isEdgeOnly());
        mDragHelper.setEdgeTrackingEnabled(mConfig.getPosition().getEdgeFlags());
        resetDrawScrimType();
    }

    //当mEdgePosition=ALL时,在低于5.0的设备版本下与edgeOnly=false,背景阴影会全屏覆盖。
    //解决方案:
    private void resetDrawScrimType() {
        if (mConfig.getPosition() == SliderPosition.ALL && !mConfig.getPosition().isEdgeOnly()) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }

    public SliderConfig getConfig() {
        return mConfig;
    }


    public ViewDragHelper getViewDragHelper() {
        return mDragHelper;
    }


    public View getSlidableChild() {
        return mSlidableChild;
    }

    /**
     * 锁定slider,不能滑动
     */
    public void lock() {
        mDragHelper.abort();
        mIsLocked = true;
    }

    /**
     * 解锁slider，可以滑动
     */
    public void unlock() {
        mDragHelper.abort();
        mIsLocked = false;
    }


    public void setOnPanelSlideListener(SliderListener listener) {
        mListener = listener;
    }

    /**
     * Initialize the slider panel
     */
    private void initDate() {
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mDragHelper = ViewDragHelper.create(this, mConfig.getSensitivity(), new ViewDragCallback());
        mDragHelper.setMinVelocity(minVel);


        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
        setConfig(mConfig);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interceptForDrag;
        if (mIsLocked) {
            return false;
        }
        if (mConfig.isEdgeOnly() && mSlidableChild != null) {
            mIsEdgeTouched = canDragFromEdge(ev);
        }
        try {
            interceptForDrag = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            interceptForDrag = false;
        }

        return interceptForDrag && !mIsLocked;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsLocked) {
            return false;
        }
        try {
            mDragHelper.processTouchEvent(event);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }


    @Override
    public void computeScroll() {
        scrimAlpha = (mScrollPercent * (mConfig.getScrimStartAlpha() - mConfig.getScrimEndAlpha())) + mConfig.getScrimEndAlpha();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * 根据手势落点判断是否可划起slidableChild
     *
     * @param ev MotionEvent事件
     * @return 是否可划起slidableChild
     */
    private boolean canDragFromEdge(MotionEvent ev) {

        float x = ev.getX();
        float y = ev.getY();

        int width = Math.min(mSlidableChild.getWidth(), getWidth());
        int height = Math.min(mSlidableChild.getHeight(), getHeight());
        float range = mConfig.getPosition().getViewSize(x, y, width, height, slidableChildLeft, slidableChildTop);
        mConfig.setEdgeSize(range);

        return mConfig.getPosition().canDragFromEdge(slidableChildLeft, slidableChildTop, x, y, range, mConfig.getEdgeSize());
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mSlidableChild;
        boolean ret = super.drawChild(canvas, child, drawingTime);

        if (scrimAlpha > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawScrim(canvas, child);
        }
        return ret;
    }


    /**
     * 根据百分比来绘制背景阴影
     *
     * @param canvas 画布
     * @param child  slidableChild
     */
    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mConfig.getScrimColor() & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * scrimAlpha);
        final int color = alpha << 24 | (mConfig.getScrimColor() & 0xffffff);
        mConfig.getPosition().setScrimRect(child, slidableChildLeft, slidableChildTop, scrimRect);
        canvas.clipRect(scrimRect, Region.Op.DIFFERENCE);
        canvas.drawColor(color);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        //sliderUi为activity,fragment刚开始进入时进行设置
        mSlidableChild = child;
        child.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!fastSlidingTag) {
                            mSlidableChild = mConfig.getSlidableMode().getSlidableChild(v);
                            if (mSlidableChild != null&&mSlidableChild==v) {
                                slidableChildLeft = mSlidableChild.getLeft();
                                slidableChildTop = mSlidableChild.getTop();
                                mSlidableChild.bringToFront();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mSlidableChild == null) {
            mSlidableChild = mConfig.getSlidableMode().getSlidableChild(null);
        }
        if (mSlidableChild != null) {
            slidableChildLeft = mSlidableChild.getLeft();
            slidableChildTop = mSlidableChild.getTop();
        }
    }


    /**
     * @param childView
     * @return child的宽度是否被包裹在slider
     */
    private boolean isWrappedViewHorizontal(View childView) {
        View slider = childView == null ? mSlidableChild : childView;
        return slider.getWidth() < getWidth();
    }

    /**
     * @param childView
     * @return child的长度是否被包裹在slider
     */
    private boolean isWrappedViewVertical(View childView) {
        View slider = childView == null ? mSlidableChild : childView;
        return slider.getHeight() < getHeight();
    }


    /**
     * The drag helper callbacks for dragging the slider attachment any directions
     */
    private class ViewDragCallback extends ViewDragHelper.Callback {

        //当mSlideChild被捕获时回调
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean isWrapped = isWrappedViewHorizontal(child) || isWrappedViewHorizontal(child);

            boolean initialTouched = isWrapped || mDragHelper.isEdgeTouched(mConfig.getPosition().getEdgeFlags(), pointerId);
            boolean captureView = mConfig.getPosition().tryCaptureView(initialTouched, mIsEdgeTouched);
            boolean edgeCase = !mConfig.isEdgeOnly() || captureView;
            boolean slidable = mSlidableChild == child;
            return edgeCase && slidable;
        }

        //返回mSlideChild在横向上能滑动的最大宽度
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mConfig.getPosition().getViewHorizontalDragRange(getWidth() - slidableChildLeft);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mConfig.getPosition().getViewVerticalDragRange(getHeight() - slidableChildTop);
        }

        //当mSlideChild位置变化时，会回调这个函数
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            boolean hWrapped = isWrappedViewHorizontal(changedView);
            boolean vWrapped = isWrappedViewVertical(changedView);

            int slideLeft = hWrapped ? left - slidableChildLeft : left;
            int slideTop = vWrapped ? top - slidableChildTop : top;

            mScrollPercent = mConfig.getPosition().onViewPositionChanged(changedView.getWidth(), changedView.getHeight(), slideLeft, slideTop);

            if (mListener != null) mListener.onSlideChange(mScrollPercent);
            invalidate();
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            final int maxWidth = getWidth();
            final int maxHeight = getHeight();

            int hThreshold = (int) (releasedChild.getWidth() * mConfig.getDistanceThresholdPercent());
            int vThreshold = (int) (releasedChild.getHeight() * mConfig.getDistanceThresholdPercent());
            boolean hOverVelocityThreshold = Math.abs(yvel) > mConfig.getVelocityThreshold();
            boolean vOverVelocityThreshold = Math.abs(xvel) > mConfig.getVelocityThreshold();
            float velocityThreshold = mConfig.getVelocityThreshold();

            boolean hWrapped = isWrappedViewHorizontal(releasedChild);
            boolean vWrapped = isWrappedViewVertical(releasedChild);

            final int left = releasedChild.getLeft();
            final int top = releasedChild.getTop();

            int endLeft = mConfig.getPosition().onViewReleasedHorizontal(hWrapped, maxWidth, left, slidableChildLeft, xvel, hThreshold, hOverVelocityThreshold, velocityThreshold);
            int endTop = mConfig.getPosition().onViewReleasedVertical(vWrapped, maxHeight, top, slidableChildTop, yvel, vThreshold, vOverVelocityThreshold, velocityThreshold);

            mDragHelper.settleCapturedViewAt(endLeft, endTop);

            invalidate();

        }


        //对mSlideChild移动时的边界范围进行控制
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            boolean hWrapped = isWrappedViewHorizontal(child);
            return mConfig.getPosition().clampViewPositionHorizontal(getWidth(), left, slidableChildLeft, hWrapped);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            boolean vWrapped = isWrappedViewVertical(child);
            return mConfig.getPosition().clampViewPositionVertical(getHeight(), top, slidableChildTop, vWrapped);
        }

        //状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
        @Override
        public void onViewDragStateChanged(int state) {

            if (mListener != null) {
                mListener.onSlideStateChanged(state);
            }
            switch (state) {
                case ViewDragHelper.STATE_IDLE:
                    fastSlidingTag = false;

                    if (isOpen()) {
                        // State Open
                        if (mListener != null) mListener.onSlideOpened();
                    } else {
                        // State Closed
                        if (mListener != null) mListener.onSlideClosed();
                        mConfig.getSlidableMode().removeSlidableChild(mSlidableChild);
                        mSlidableChild = null;
                    }
                    break;
                case ViewDragHelper.STATE_DRAGGING:
                    break;
                case ViewDragHelper.STATE_SETTLING:
                    if (!isOpen()) {
                        fastSlidingTag = true;
                    }

                    break;
            }

        }
    }

    /**
     * 表示mSlidableChild是否在目标位置
     *
     * @return true   在目标位置否则不在
     */
    private boolean isOpen() {
        return mSlidableChild != null && mConfig.getPosition().onViewDragStateChanged(mSlidableChild.getLeft(), mSlidableChild.getTop(), slidableChildLeft, slidableChildTop);
    }
}
