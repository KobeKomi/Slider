package com.komi.slider.mode;

import android.support.v4.util.SparseArrayCompat;
import android.view.View;

/**
 * Created by Komi on 2016/2/21.
 */
public class CustomMode extends SlidableMode {
    private static CustomMode ourInstance = new CustomMode();

    public static CustomMode getInstance() {
        return ourInstance;
    }

    private SparseArrayCompat<View> customSlidableChildrenList = new SparseArrayCompat<>();

    private CustomMode() {
    }

    @Override
    public View getSlidableChild(View touchChild) {
        return customSlidableChildrenList.get(getViewKey(touchChild));
    }



    @Override
    public void removeSlidableChild(View child) {
        customSlidableChildrenList.remove(getViewKey(child));
    }

    @Override
    public void addCustomSlidableChild(View... children) {
        for (View child : children) {
            customSlidableChildrenList.put(child.hashCode(), child);
        }

    }

    private int getViewKey(View child)
    {
        return child!=null?child.hashCode():0;
    }
}
