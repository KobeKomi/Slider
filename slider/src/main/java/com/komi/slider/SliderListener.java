package com.komi.slider;

/**
 * This listener interface is for receiving events from the sliding panel such as state changes
 * and slide progress
 * 
 * Created by Komi on 2016-01-21.
 */
public interface SliderListener {

    /**
     * This is called when the {@link ViewDragHelper} calls it's
     * state change callback.
     *
     * @see ViewDragHelper#STATE_IDLE
     * @see ViewDragHelper#STATE_DRAGGING
     * @see ViewDragHelper#STATE_SETTLING
     *
     * @param state     the {@link ViewDragHelper} state
     */
    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    void onSlideClosed();

}
