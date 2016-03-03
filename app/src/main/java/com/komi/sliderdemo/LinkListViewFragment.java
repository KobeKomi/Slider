package com.komi.sliderdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.komi.slider.position.SliderPosition;
import com.komi.slider.ui.SliderFragment;

/**
 * Created by Komi on 2016-02-05.
 */
public class LinkListViewFragment extends SliderFragment implements FragmentCloseListener, AbsListView.OnScrollListener {

    private View rootView;
    private ListView listView;

    @Override
    public View creatingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listview_link, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) iSlider.getSliderView().findViewById(R.id.extends_fragment_list);
        listView.setOnScrollListener(this);

        ArrayAdapter<String> demoAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1);

        for (int i = 0; i < 50; i++) {
            demoAdapter.add("--------:" + i);
        }
        listView.setAdapter(demoAdapter);
        iSlider.getConfig().setPosition(SliderPosition.BOTTOM);
        iSlider.getConfig().setEdgeOnly(false);
        iSlider.lock();
    }

    @Override
    public void fragmentClosed() {
        if(iSlider!=null)
            iSlider.slideExit();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                iSlider.unlock();
            }
        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            iSlider.lock();

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
