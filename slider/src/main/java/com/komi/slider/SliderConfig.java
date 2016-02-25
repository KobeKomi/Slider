package com.komi.slider;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

import com.komi.slider.mode.SlidableMode;
import com.komi.slider.position.SliderPosition;

/**
 * This class contains the configuration information for all the options available in
 * this library
 *
 * Created by Komi on 2016-01-21.
 */
public class SliderConfig {

    public static final int   DEFAULT_PRIMARY_COLOR = -1;
    public static final int   DEFAULT_SECONDARY_COLOR = -1;
    public static final float DEFAULT_EDGE_SIZE=0;
    public static final float DEFAULT_EDGE_PERCENT=0.18f;
    public static final float DEFAULT_SENSITIVITY=1f;
    public static final int   DEFAULT_SCRIM_COLOR=Color.BLACK;
    public static final float DEFAULT_SCRIM_START_ALPHA=0.8f;
    public static final float DEFAULT_SCRIM_END_ALPHA=0f;
    public static final float DEFAULT_VELOCITY_THRESHOLD=5f;
    public static final float DEFAULT_DISTANCE_THRESHOLD_PERCENT=0.25f;

    /**
     * 状态栏初始颜色
     */
    private int primaryColor = DEFAULT_PRIMARY_COLOR;

    /**
     * 状态栏目的颜色
     */
    private int secondaryColor = DEFAULT_SECONDARY_COLOR;

    /**
     * 可滑起触摸区域大小
     */
    private float edgeSize = DEFAULT_EDGE_SIZE;

    /**
     * 可滑动区域百分比
     */
    private float edgePercent = DEFAULT_EDGE_PERCENT;

    /**
     * 滑动触摸灵敏度
     */
    private float sensitivity = DEFAULT_SENSITIVITY;
    /**
     * 拉开背景颜色
     */
    private int scrimColor = DEFAULT_SCRIM_COLOR;
    /**
     * 开始拉开背景的颜色透明度
     */
    private float scrimStartAlpha = DEFAULT_SCRIM_START_ALPHA;
    /**
     * 结束拉开背景的颜色透明度
     */
    private float scrimEndAlpha = DEFAULT_SCRIM_END_ALPHA;
    /**
     * 速度阈值
     */
    private float velocityThreshold = DEFAULT_VELOCITY_THRESHOLD;
    /**
     * 拉开距离比例阈值
     */
    private float distanceThresholdPercent = DEFAULT_DISTANCE_THRESHOLD_PERCENT;
    /**
     * 是否边缘可滑动，true:边缘可滑动，false:全屏可滑动
     */
    private boolean edgeOnly =true;

    /**
     * 是否滑动进入页面
     */
    private boolean slideEnter =true;

    /**
     * 是否滑动退出页面
     */
    private boolean slideExit =true;

    private SliderPosition position = SliderPosition.LEFT;

    private SliderListener listener;

    private SlidableMode slidableMode = SlidableMode.ALL;

    private boolean finishUi=true;

    private boolean slidable=true;

    /**
     * Hidden Constructor
     * Use the builder pattern
     */
    private SliderConfig(){}

    /***********************************************************************************************
     *
     * Getters
     *
     */

    /**
     * Get the primary color that the slider will interpolate. That is this color is the color
     * of the status bar of the Activity you are returning to
     *
     * @return      the primary status bar color
     */
    public int getPrimaryColor(){
        return primaryColor;
    }

    /**
     * Get the secondary color that the slider will interpolatel That is the color of the Activity
     * that you are making slidable
     *
     * @return      the secondary status bar color
     */
    public int getSecondaryColor(){
        return secondaryColor;
    }

    /**
     * Get the color of the background scrim
     *
     * @return      the scrim color integer
     */
    public int getScrimColor(){
        return scrimColor;
    }

    /**
     * Get teh start alpha value for when the activity is not swiped at all
     *
     * @return      the start alpha value (0.0 to 1.0)
     */
    public float getScrimStartAlpha(){
        return scrimStartAlpha;
    }

    /**
     * Get the end alpha value for when the user almost swipes the activity off the screen
     *
     * @return      the end alpha value (0.0 to 1.0)
     */
    public float getScrimEndAlpha(){
        return scrimEndAlpha;
    }

    /**
     * Get the position of the slidable mechanism for this configuration. This is the position on
     * the screen that the user can swipe the activity away from
     *
     * @return      the slider position
     */
    public SliderPosition getPosition(){
        return position;
    }

    /**
     * Get the touch 'width' to be used in the gesture detection. This value should incorporate with
     * the device's touch slop
     *
     * @return      the touch area size
     */
    public float getEdgeSize(){
        return edgeSize;
    }

    /**
     * Get the velocity threshold at which the slide action is completed regardless of offset
     * distance of the drag
     *
     * @return      the velocity threshold
     */
    public float getVelocityThreshold(){
        return velocityThreshold;
    }

    /**
     * Get at what % of the screen is the minimum viable distance the activity has to be dragged
     * in-order to be slinged off the screen
     *
     * @return      the distant threshold as a percentage of the screen size (width or height)
     */
    public float getDistanceThresholdPercent(){
        return distanceThresholdPercent;
    }

    /**
     * Get the touch sensitivity set in the {@link ViewDragHelper} when
     * creating it.
     *
     * @return      the touch sensitivity
     */
    public float getSensitivity(){
        return sensitivity;
    }

    /**
     * Get the slider listener set by the user to respond to certain events in the sliding
     * mechanism.
     *
     * @return      the slidr listener
     */
    public SliderListener getListener(){
        return listener;
    }

    /**
     * Return whether or not the set status bar colors are valid
     * @return true if primary and secondary color are set
     */
    public boolean areStatusBarColorsValid(){
        return primaryColor != -1 && secondaryColor != -1;
    }

    /**
     * Has the user configured slider to only catch at the edge of the screen ?
     * @return      true if is edge capture only
     */
    public boolean isEdgeOnly() {
        return edgeOnly;
    }

    /**
     * Get the size of the edge field that is catchable
     *
     * @see #isEdgeOnly()
     * @return      the size of the edge that is grabable
     */
    public float getEdgePercent() {
        return edgePercent;
    }


    public boolean isSlideEnter() {
        return slideEnter;
    }

    public boolean isSlideExit() {
        return slideExit;
    }

    public SlidableMode getSlidableMode() {
        return slidableMode;
    }

    public boolean isFinishUi() {
        return finishUi;
    }

    public boolean isSlidable() {
        return slidable;
    }

    /***********************************************************************************************
     *
     * Setters
     *
     */

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setEdgeSize(float range) {
        this.edgeSize = range*edgePercent;
    }

    public void setEdgePercent(@FloatRange(from = 0f, to = 1f) float edgePercent) {
        this.edgePercent = edgePercent;
    }

    public void setScrimColor(int scrimColor) {
        this.scrimColor = scrimColor;
    }

    public void setScrimStartAlpha(float scrimStartAlpha) {
        this.scrimStartAlpha = scrimStartAlpha;
    }

    public void setScrimEndAlpha(float scrimEndAlpha) {
        this.scrimEndAlpha = scrimEndAlpha;
    }

    public void setVelocityThreshold(float velocityThreshold) {
        this.velocityThreshold = velocityThreshold;
    }

    public void setDistanceThresholdPercent(float distanceThresholdPercent) {
        this.distanceThresholdPercent = distanceThresholdPercent;
    }

    public void setEdgeOnly(boolean edgeOnly) {
        this.edgeOnly = edgeOnly;
    }


    public void setPosition(SliderPosition position) {
        this.position = position;
    }

    public void setListener(SliderListener listener) {
        this.listener = listener;
    }

    public void setSlideEnter(boolean slideEnter) {
        this.slideEnter = slideEnter;
    }

    public void setSlideExit(boolean slideExit) {
        this.slideExit = slideExit;
    }

    public void setSlidableMode(SlidableMode slidableMode) {
        this.slidableMode = slidableMode;
    }


    public void setFinishUi(boolean finishUi) {
        this.finishUi = finishUi;
    }

    public void setSlidable(boolean slidable) {
        this.slidable = slidable;
    }

    /**
     * The Builder for this configuration class. This is the only way to create a
     * configuration
     */
    public static class Builder{

        private SliderConfig config;

        public Builder(){
            config = new SliderConfig();
        }

        public Builder primaryColor(@ColorInt int color){
            config.primaryColor = color;
            return this;
        }

        public Builder secondaryColor(@ColorInt int color){
            config.secondaryColor = color;
            return this;
        }

        public Builder position(SliderPosition position){
            config.position = position;
            return this;
        }

        public Builder edgeOnly(boolean edgeOnly){
            config.edgeOnly = edgeOnly;
            return this;
        }


        public Builder sensitivity(float sensitivity){
            config.sensitivity = sensitivity;
            return this;
        }

        public Builder scrimColor(@ColorInt int color){
            config.scrimColor = color;
            return this;
        }

        public Builder scrimStartAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha){
            config.scrimStartAlpha = alpha;
            return this;
        }

        public Builder scrimEndAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha){
            config.scrimEndAlpha = alpha;
            return this;
        }

        public Builder velocityThreshold(float threshold){
            config.velocityThreshold = threshold;
            return this;
        }

        public Builder distanceThreshold(@FloatRange(from = .1f, to = .9f) float threshold){
            config.distanceThresholdPercent = threshold;
            return this;
        }

        public Builder edge(boolean flag){
            config.edgeOnly = flag;
            return this;
        }

        public Builder edgePercent(@FloatRange(from = 0f, to = 1f) float edgeSize){
            config.edgePercent = edgeSize;
            return this;
        }
        public Builder edgeSize(float edgeSize){
            config.edgeSize = edgeSize;
            return this;
        }

        public Builder listener(SliderListener listener){
            config.listener = listener;
            return this;
        }

        public Builder slideEnter(boolean slideEnter){
            config.slideEnter = slideEnter;
            return this;
        }

        public Builder slideExit(boolean slideExit){
            config.slideExit = slideExit;
            return this;
        }

        public Builder finishUi(boolean finishUi){
            config.finishUi = finishUi;
            return this;
        }

        public Builder slidable(boolean slidable){
            config.slidable = slidable;
            return this;
        }
        public SliderConfig build(){
            return config;
        }

    }

}
