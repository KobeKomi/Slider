package com.komi.sliderdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komi.slider.SliderConfig;
import com.komi.slider.SliderListenerAdapter;
import com.komi.slider.ui.SliderDialogFragment;

/**
 * Created by Komi on 2016-03-02.
 */
public class SampleDialogFragment extends SliderDialogFragment implements FragmentCloseListener {

    private View rootView;
    private Dialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
        mConfig = new SliderConfig.Builder()
                .scrimStartAlpha(0)
                .scrimEndAlpha(0)
                .edgeOnly(false)
                .listener(listener)
                .position(DemoUtils.getRandomPosition())
                .build();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.DialogTheme) {
            @Override
            public void onBackPressed() {
                if (iSlider != null) {
                    iSlider.slideExit();
                }
            }
        };
        return mDialog;
    }

    @Override
    public Dialog getDialog() {
        return mDialog;
    }


    @Override
    public void fragmentClosed() {

    }

    private SliderListenerAdapter listener =new SliderListenerAdapter() {
        @Override
        public void onSlideChange(float percent) {
        }

        @Override
        public void onSlideOpened() {
        }

        @Override
        public void onSlideClosed() {

        }
    };
}
