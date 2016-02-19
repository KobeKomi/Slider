package com.komi.slider;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * This class contains the configuration information for all the options available in
 * this library
 *
 * Created by Komi on 2016-01-21.
 */
public class SliderConfig {

    private int colorPrimary = -1;
    private int colorSecondary = -1;
    /**
     * 滑动触摸区域大小
     */
    private float edgeRange = 0;

    /**
     * 可滑动区域百分比
     */
    private float edgePercent = 0.18f;

    /**
     * 滑动触摸灵敏度
     */
    private float sensitivity = 1f;
    /**
     * 拉开背景颜色
     */
    private int scrimColor = Color.BLACK;
    /**
     * 开始拉开背景的颜色透明度
     */
    private float scrimStartAlpha = 0.8f;
    /**
     * 结束拉开背景的颜色透明度
     */
    private float scrimEndAlpha = 0f;
    /**
     * 速度阈值
     */
    private float velocityThreshold = 5f;
    /**
     * 拉开距离比例阈值
     */
    private float distanceThresholdPercent = 0.25f;
    /**
     * 是否边缘可滑动，true:边缘可滑动，false:全屏可滑动
     */
    private boolean edgeOnly =true;

    /**
     * 是否滑动进入页面
     */
    private boolean slideEnter =true;
    private boolean slideExit =true;

    private SliderPosition position = SliderPosition.LEFT;
    private SliderListener listener;

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
        return colorPrimary;
    }

    /**
     * Get the secondary color that the slider will interpolatel That is the color of the Activity
     * that you are making slidable
     *
     * @return      the secondary status bar color
     */
    public int getSecondaryColor(){
        return colorSecondary;
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
    public float getEdgeRange(){
        return edgeRange ;
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
        return colorPrimary != -1 && colorSecondary != -1;
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


    public float getEdgeSize()
    {
        return edgePercent*edgeRange;
    }

    public boolean isSlideEnter() {
        return slideEnter;
    }

    public boolean isSlideExit() {
        return slideExit;
    }

    /***********************************************************************************************
     *
     * Setters
     *
     */

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public void setColorSecondary(int colorSecondary) {
        this.colorSecondary = colorSecondary;
    }

    public void setEdgeRange(float edgeRange) {
        this.edgeRange = edgeRange;
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
            config.colorPrimary = color;
            return this;
        }

        public Builder secondaryColor(@ColorInt int color){
            config.colorSecondary = color;
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

        public Builder edgeRange(float range){
            config.edgeRange = range;
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

        public SliderConfig build(){
            return config;
        }

    }

}
