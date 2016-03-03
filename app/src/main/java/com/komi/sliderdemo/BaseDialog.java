package com.komi.sliderdemo;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Komi on 2016-03-02.
 */
public class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }


    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
