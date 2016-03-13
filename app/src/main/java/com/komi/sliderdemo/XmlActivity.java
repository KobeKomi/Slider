package com.komi.sliderdemo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.komi.slider.Slider;
import com.komi.slider.SliderListener;
import com.komi.slider.mode.SlidableMode;
import com.komi.slider.position.SliderPosition;
import com.komi.slider.ui.SliderAppCompatActivity;

/**
 * Created by Komi on 2016/2/16.
 */
public class XmlActivity extends SliderAppCompatActivity implements SliderListener{

    private Slider sliderLayout;
    private int imgSizeWidth;
    private int imgSizeHeight;
    private int marginTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        setTitle(Demo.XML_ACTIVITY.titleResId);
        sliderLayout=(Slider)findViewById(R.id.xml_slider_layout);
        sliderLayout.setOnPanelSlideListener(this);
        sliderLayout.getConfig().setScrimStartAlpha(0);
        sliderLayout.getConfig().setScrimEndAlpha(0);
        sliderLayout.getConfig().setSlidableMode(SlidableMode.SINGLE);
        imgSizeWidth=(int)getResources().getDimension(R.dimen.xml_img_width);
        imgSizeHeight =(int)getResources().getDimension(R.dimen.xml_img_height);
        marginTop=(int)getResources().getDimension(R.dimen.xml_img_margin_top);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_xml, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_xml_add:
                addChildView();
                return true;

            case R.id.menu_xml_del:
                View slidableChild=sliderLayout.getSlidableChild();
                sliderLayout.getConfig().setPosition(DemoUtils.getRandomPosition());
                if(slidableChild!=null) {
                    int[] exitTarget = sliderLayout.getConfig().getPosition().getExitTarget(sliderLayout.getSlidableChild(), sliderLayout.getWidth(), sliderLayout.getHeight());
                    sliderLayout.getViewDragHelper().smoothSlideViewTo(slidableChild, exitTarget[2], exitTarget[3]);
                    sliderLayout.invalidate();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onSlideStateChanged(int state) {

    }

    @Override
    public void onSlideChange(float percent) {

    }

    @Override
    public void onSlideOpened() {

    }

    @Override
    public void onSlideClosed() {

        if(sliderLayout.getSlidableChild()!=null)
        {
            sliderLayout.removeView(sliderLayout.getSlidableChild());
        }

    }

    private void addChildView()
    {
        if(sliderLayout.getChildCount()<10) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(DemoUtils.getRandomPicRes2());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(imgSizeWidth, imgSizeHeight);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.topMargin = sliderLayout.getChildCount() * marginTop;
            imageView.setLayoutParams(params);
            sliderLayout.addView(imageView);
            sliderLayout.getConfig().setPosition(SliderPosition.ALL);
            //custom enter animation
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
            ObjectAnimator.ofPropertyValuesHolder(imageView, pvhY,pvhZ).setDuration(400).start();
        }
    }
}
