package com.komi.slider.position;

import android.view.View;

/**
 * Created by Komi on 2016-03-04.
 * 区分slider的本身属性责任,当外部情况遇到不同Position时，此类帮助扩展
 */
public interface PositionExtensions {

    /**
     * @return 当ui为activity时，获取当前方向的动画
     */
     int[] getActivitySlidingAmins();

    /**
     * 加入slidableChild时要达到的坐标，左进（右出），右进（左出），上进（下出），下进(上出)
     *
     * @param childView slidableChild
     * @param maxWidth  达到的宽度距离
     * @param maxHeight 达到的高度距离
     * @return 目标坐标值(startLeft,startTop,finalLeft, finalTop)
     */
     int[] getEnterTarget(View childView, int maxWidth, int maxHeight);

    /**
     * 移除slidableChild时要达到的坐标,（左进）右出，（右进）左出，（上进）下出，(下进)上出
     *
     * @param childView slidableChild
     * @param maxWidth  达到的宽度距离
     * @param maxHeight 达到的高度距离
     * @return 目标坐标值(startLeft,startTop,finalLeft, finalTop)
     */
     int[] getExitTarget(View childView,int maxWidth,int maxHeight);
}
