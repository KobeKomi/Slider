package com.komi.slider.mode;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Komi on 2016/2/21.
 */
public class SingleMode extends SlidableMode {
    private static SingleMode ourInstance = new SingleMode();



    public static SingleMode getInstance() {
        return ourInstance;
    }

    private SingleMode() {
    }


    @Override
    public View getSlidableChild(View touchChild) {
        ViewGroup parent=null;
        if(touchChild!=null) {
             parent = (ViewGroup) touchChild.getParent();
        }
        return parent!=null&&parent.getChildAt(parent.getChildCount()-1)==touchChild?touchChild:null;
    }


}
