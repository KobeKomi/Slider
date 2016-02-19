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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    private View mSlideChild;

    //slide布局时的坐标
    private int slideChildLeft;
    private int slideChildTop;

    private ViewDragHelper mDragHelper;
    private SliderListener mListener;

    private boolean mIsLocked = false;
    private boolean mIsEdgeTouched = false;
    private SliderPosition mEdgePosition;
    private SliderConfig mConfig;
    private boolean slideBegin;


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
        int edgeFlag = a.getInt(R.styleable.SliderStyle_position, 0);
        boolean edgeOnly=a.getBoolean(R.styleable.SliderStyle_edgeOnly,false);
        a.recycle();

        mConfig=new SliderConfig.Builder()
                .position(SliderPosition.getSliderPosition(edgeFlag))
                .edgeOnly(edgeOnly)
                .build();
        initDate();
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        this.mSlideChild =child;
    }


    public void setConfig(SliderConfig config) {
        this.mConfig = config;
        mEdgePosition = mConfig.getPosition();
        mEdgePosition.setEdgeOnly(mConfig.isEdgeOnly());
        mDragHelper.setEdgeTrackingEnabled(mEdgePosition.getEdgeFlags());
        resetDrawScrimType();
    }

    //当mEdgePosition=ALL时,在低于5.0的设备版本下与edgeOnly=false,背景阴影会全屏覆盖。
    //解决方案:
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
        if (!slideBegin&& mSlideChild !=null) {
            Rect rect = mEdgePosition.getSlidingInRect(mSlideChild);
            mDragHelper.smoothSlideViewTo(mSlideChild, rect.left, rect.top, rect.right, rect.bottom);
            slideBegin = true;
        }
    }


    /**
     * Scroll out contentView
     */
    public void slideExit() {
        if(mSlideChild !=null) {
            Rect rect = mEdgePosition.getSlidingOutRect(mSlideChild);
            mDragHelper.smoothSlideViewTo(mSlideChild, rect.left, rect.top, rect.right, rect.bottom);
            invalidate();
        }

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

        if (mConfig.isEdgeOnly()&&mSlideChild!=null) {
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


    private boolean canDragFromEdge(MotionEvent ev) {

        float x = ev.getX();
        float y = ev.getY();
        int width=Math.min(mSlideChild.getWidth(),getWidth());
        int height=Math.min(mSlideChild.getHeight(),getHeight());

        float size = mEdgePosition.getViewSize(x, y, width, height);
        mConfig.setEdgeRange(size);

        return mEdgePosition.canDragFromEdge(slideChildLeft,slideChildTop,x, y, size, mConfig.getEdgeSize());
    }

    /**
     * The drag helper callbacks for dragging the slider attachment any directions
     */
    private class ViewDragCallback extends ViewDragHelper.Callback {

        //当child被捕获时回调
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean isWrapped=child.getWidth()<getWidth()||child.getHeight()<getHeight();

            boolean initialTouched=isWrapped||mDragHelper.isEdgeTouched(mEdgePosition.getEdgeFlags(),pointerId);
            boolean captureView=mEdgePosition.tryCaptureView(initialTouched, mIsEdgeTouched);
            boolean edgeCase = !mConfig.isEdgeOnly() ||captureView;
            return edgeCase;
        }

        //返回mDecorView在横向上能滑动的最大距离
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mEdgePosition.getViewHorizontalDragRange(getWidth());
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mEdgePosition.getViewVerticalDragRange(getHeight());
        }

        //当mDecorView位置变化时，会回调这个函数
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //-----------------------------xxxxxxxxxxxxxxxxxxxxxx
            boolean hWrapped=changedView.getWidth()<getWidth();
            boolean vWrapped=changedView.getHeight()<getHeight();

            int slideLeft=hWrapped?slideChildLeft+left:left;
            int slideTop=vWrapped?slideChildTop:top;

            super.onViewPositionChanged(changedView, slideLeft, slideTop, dx, dy);

            Log.i("KOMI","changedView_LEFT:"+changedView.getLeft()+"----left:"+left+"-----slideLeft:"+slideChildLeft);
            mScrollPercent = mEdgePosition.onViewPositionChanged(changedView.getWidth(), changedView.getHeight(),left, top,slideChildLeft,slideChildTop);

            if (mListener != null) mListener.onSlideChange(mScrollPercent);
            invalidate();
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            final int maxWidth =getWidth();
            final int maxHeight =getHeight();

            int hThreshold = (int) (releasedChild.getWidth() * mConfig.getDistanceThresholdPercent());
            int vThreshold = (int) (releasedChild.getHeight() * mConfig.getDistanceThresholdPercent());
            boolean hSwiping = Math.abs(yvel) > mConfig.getVelocityThreshold();
            boolean vSwiping = Math.abs(xvel) > mConfig.getVelocityThreshold();
            float velocityThreshold = mConfig.getVelocityThreshold();

            boolean hWrapped=releasedChild.getWidth()<getWidth();
            boolean vWrapped=releasedChild.getHeight()<getHeight();

            final int left = hWrapped?(releasedChild.getLeft()-slideChildLeft):releasedChild.getLeft();
            final int top = vWrapped?(releasedChild.getTop()-slideChildTop):releasedChild.getTop();

            int settleLeft = mEdgePosition.onViewReleasedHorizontal(hWrapped,maxWidth, left, xvel, hThreshold, hSwiping, velocityThreshold);
            int settleTop  = mEdgePosition.onViewReleasedVertical(vWrapped,maxHeight, top, yvel, vThreshold, vSwiping, velocityThreshold);

            Log.i("KOMI","--settleLeft:"+settleLeft+"------settleTop:"+settleTop);
            Log.i("KOMI","--left:"+left+"--------childLeft:"+releasedChild.getLeft()+"-----slideChildLeft:"+slideChildLeft);

            mDragHelper.settleCapturedViewAt(settleLeft, settleTop);
            invalidate();

        }


        //此方法返回一个值，告诉Helper，这个view能滑动的最大（或者负向最大）的横向坐标
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return mEdgePosition.clampViewPositionHorizontal(getWidth(), left, dx);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return mEdgePosition.clampViewPositionVertical(getHeight(), top, dy);
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
                    boolean isOpen = mSlideChild !=null&&mEdgePosition.onViewDragStateChanged(mSlideChild.getLeft(), mSlideChild.getTop());
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
        final boolean drawContent = child == mSlideChild;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (scrimAlpha > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawScrim(canvas, child);
        }
        return ret;
    }


    /**
     * @param canvas
     * @param child
     */
    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mConfig.getScrimColor() & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * scrimAlpha);
        final int color = alpha << 24 | (mConfig.getScrimColor() & 0xffffff);
        mEdgePosition.setScrimRect(child, scrimRect);
        canvas.clipRect(scrimRect, Region.Op.DIFFERENCE);
        canvas.drawColor(color);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        slideChildLeft=mSlideChild.getLeft();
        slideChildTop=mSlideChild.getTop();
    }
}
