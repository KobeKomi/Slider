/*
 * Copyright (c) 2014. 52inc
 * All Rights Reserved.
 */

package com.komi.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Komi on 2016-01-21.
 */
public class Slider extends FrameLayout {

    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    //拉开的背景
    private Rect scrimRect = new Rect();
    private float scrimAlpha;
    private float mScrollPercent;

    //ui的view
    private View mDecorView;
    private ViewDragHelper mDragHelper;
    private SliderListener mListener;

    private boolean mIsLocked = false;
    private boolean mIsEdgeTouched = false;
    private SliderPosition mEdgePosition;
    private SliderConfig mConfig;
    private boolean slideBegin;

    /**
     * 如果调用了此构造函数，必须调用
     *
     * @see #setDecorView(View)
     */
    public Slider(Context context) {
        super(context);
        mConfig = new SliderConfig.Builder().build();
    }

    public Slider(Context context, View decorView) {
        this(context, decorView, null);
    }

    public Slider(Context context, View decorView, SliderConfig config) {
        super(context);
        mDecorView = decorView;
        mConfig = (config == null ? new SliderConfig.Builder().build() : config);
        if (decorView != null) {
            initDate();
        }
    }

    public void setDecorView(View decorView) {
        this.mDecorView = decorView;
        slideBegin = false;
        if (decorView != null) {
            initDate();
        }
    }

    public void setConfig(SliderConfig config) {
        this.mConfig = config;
        mEdgePosition = mConfig.getPosition();
        mEdgePosition.setEdgeOnly(mConfig.isEdgeOnly());
        mDragHelper.setEdgeTrackingEnabled(mEdgePosition.getEdgeFlags());
        resetDrawScrimType();
    }

    //ALL在背景阴影的解决方案
    private void resetDrawScrimType() {
        if (mEdgePosition == SliderPosition.ALL && !mEdgePosition.isEdgeOnly()) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }

    public SliderConfig getConfig() {
        return mConfig;
    }

    /**
     * Lock this sliding panel to ignore touch inputs.
     */
    public void lock() {
        mDragHelper.abort();
        mIsLocked = true;
    }

    /**
     * Unlock this sliding panel to listen to touch inputs.
     */
    public void unlock() {
        mDragHelper.abort();
        mIsLocked = false;
    }


    public void slideEnter() {
        //初始化的时候mDecorView的宽高可能为0
        if (!slideBegin) {
            Rect rect = mEdgePosition.getSlidingInRect(mDecorView);
            mDragHelper.smoothSlideViewTo(mDecorView, rect.left, rect.top, rect.right, rect.bottom);
            slideBegin = true;
        }
    }


    /**
     * Scroll out contentView
     */
    public void slideExit() {
        Rect rect = mEdgePosition.getSlidingOutRect(mDecorView);
        mDragHelper.smoothSlideViewTo(mDecorView, rect.left, rect.top, rect.right, rect.bottom);
        invalidate();

    }


    /**
     * Set the panel slide listener that gets called based on slider changes
     *
     * @param listener callback implementation
     */
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

        //false：全屏滑动，ture:边缘滑动
        if (mConfig.isEdgeOnly()) {
            mIsEdgeTouched = canDragFromEdge(ev);
        }

        // Fix for pull request #13 and issue #12
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


    private boolean canDragFromEdge(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        float size = mEdgePosition.getViewSize(x, y, getWidth(), getHeight());
        mConfig.setEdgeRange(size);
        return mEdgePosition.canDragFromEdge(x, y, size, mConfig.getEdgeSize());
    }

    /**
     * The drag helper callbacks for dragging the slider attachment any directions
     */
    private class ViewDragCallback extends ViewDragHelper.Callback {

        //当child被捕获时回调
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean edgeCase = !mConfig.isEdgeOnly() || mEdgePosition.tryCaptureView(mDragHelper.isEdgeTouched(mEdgePosition.getEdgeFlags(), pointerId), mIsEdgeTouched);
            return edgeCase;
        }

        //返回指定View在横向上能滑动的最大距离
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mEdgePosition.getViewHorizontalDragRange(child.getWidth());
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mEdgePosition.getViewVerticalDragRange(child.getHeight());
        }

        //当子视图位置变化时，会回调这个函数
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mScrollPercent = mEdgePosition.onViewPositionChanged(changedView.getWidth(), changedView.getHeight(), left, top);
            if (mListener != null) mListener.onSlideChange(mScrollPercent);
            invalidate();
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            final int childWidth = releasedChild.getWidth();
            final int childHeight = releasedChild.getHeight();
            final int left = releasedChild.getLeft();
            final int top = releasedChild.getTop();
            int hThreshold = (int) (childWidth * mConfig.getDistanceThresholdPercent());
            int vThreshold = (int) (childHeight * mConfig.getDistanceThresholdPercent());
            boolean hSwiping = Math.abs(yvel) > mConfig.getVelocityThreshold();
            boolean vSwiping = Math.abs(xvel) > mConfig.getVelocityThreshold();
            float velocityThreshold = mConfig.getVelocityThreshold();

            int settleLeft = mEdgePosition.onViewReleasedHorizontal(childWidth, left, xvel, hThreshold, hSwiping, velocityThreshold);
            int settleTop = mEdgePosition.onViewReleasedVertical(childHeight, top, yvel, vThreshold, vSwiping, velocityThreshold);

            mDragHelper.settleCapturedViewAt(settleLeft, settleTop);
            invalidate();

        }


        //此方法返回一个值，告诉Helper，这个view能滑动的最大（或者负向最大）的横向坐标
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return mEdgePosition.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return mEdgePosition.clampViewPositionVertical(child, top, dy);
        }

        //状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);

            if (mListener != null) {
                mListener.onSlideStateChanged(state);
            }

            switch (state) {
                case ViewDragHelper.STATE_IDLE:
                    boolean isOpen = mEdgePosition.onViewDragStateChanged(mDecorView.getLeft(), mDecorView.getTop());
                    if (isOpen) {
                        // State Open
                        if (mListener != null) mListener.onSlideOpened();
                    } else {
                        // State Closed
                        if (mListener != null) mListener.onSlideClosed();
                    }
                    break;
                case ViewDragHelper.STATE_DRAGGING:
                    break;
                case ViewDragHelper.STATE_SETTLING:
                    break;
            }

        }
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mDecorView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (scrimAlpha > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mConfig.getScrimColor() & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * scrimAlpha);
        final int color = alpha << 24 | (mConfig.getScrimColor() & 0xffffff);
        mEdgePosition.setScrimRect(child, scrimRect);
        canvas.clipRect(scrimRect, Region.Op.DIFFERENCE);
        canvas.drawColor(color);
    }

}
