package com.komi.sliderdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.komi.slider.ISlider;
import com.komi.slider.Slider;
import com.komi.slider.SliderUtils;
import com.komi.slider.mode.SlidableMode;
import com.komi.slider.position.SliderPosition;

/**
 * Created by Komi on 2016/2/16.
 */
public class CustomActivity extends AppCompatActivity implements View.OnClickListener {

    private Slider sliderLayout;
    private final int IMG_SIZE = 250;
    private final int LEFT_MARGIN = 20;
    private final int TOP_MARGIN = 30;
    private int row = 0;
    private int column = 0;
    private ISlider activitySlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        setTitle(this.getClass().getSimpleName());
        sliderLayout = (Slider) findViewById(R.id.custom_slider_layout);
        activitySlider=SliderUtils.attachActivity(this,null);
        activitySlider.getConfig().setSecondaryColor(Color.TRANSPARENT);

        SliderUtils.attachView(this,sliderLayout,null);
        sliderLayout.getConfig().setScrimStartAlpha(0);
        sliderLayout.getConfig().setScrimEndAlpha(0);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_custom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getGroupId()) {
            case R.id.menu_group_slider_position:
                item.setChecked(true);
                break;
            case R.id.menu_group_slider_sliable_mode:
                item.setChecked(true);
                break;
        }
        switch (item.getItemId()) {
            case R.id.menu_add:
                addSliderChild();
                break;
            case R.id.menu_del:
                sliderLayout.removeAllViews();
                sliderLayout.getConfig().getSlidableMode().clearSlidableChildren();
                row = 0;
                column = 0;
                break;

            case R.id.menu_position_left:
                sliderLayout.getConfig().setPosition(SliderPosition.LEFT);
                break;
            case R.id.menu_position_right:
                sliderLayout.getConfig().setPosition(SliderPosition.RIGHT);
                break;
            case R.id.menu_position_top:
                sliderLayout.getConfig().setPosition(SliderPosition.TOP);
                break;
            case R.id.menu_position_bottom:
                sliderLayout.getConfig().setPosition(SliderPosition.BOTTOM);
                break;
            case R.id.menu_position_horizontal:
                sliderLayout.getConfig().setPosition(SliderPosition.HORIZONTAL);
                break;
            case R.id.menu_position_vertical:
                sliderLayout.getConfig().setPosition(SliderPosition.VERTICAL);
                break;
            case R.id.menu_position_all:
                sliderLayout.getConfig().setPosition(SliderPosition.ALL);
                break;

            case R.id.menu_mode_all:
                sliderLayout.getConfig().setSlidableMode(SlidableMode.ALL);
                break;
            case R.id.menu_mode_single:
                sliderLayout.getConfig().setSlidableMode(SlidableMode.SINGLE);
                break;
            case R.id.menu_mode_custom:
                sliderLayout.getConfig().setSlidableMode(SlidableMode.CUSTOM);
                break;
        }

        return true;

    }


    private void addSliderChild() {
        if (sliderLayout.getChildCount() == 0) {
            row = 0;
            column = 0;
        }
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(DemoUtils.getRandomPicRes());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(IMG_SIZE, IMG_SIZE);
        int childCount = sliderLayout.getChildCount();
        params.gravity = Gravity.TOP;
        int maxColumn = sliderLayout.getWidth() / (LEFT_MARGIN + IMG_SIZE);
        column = childCount % maxColumn;
        boolean overWidth = column + 1 == maxColumn;
        params.leftMargin = column * (LEFT_MARGIN + IMG_SIZE);


        int maxRow = sliderLayout.getHeight() / (LEFT_MARGIN + IMG_SIZE);

        params.topMargin = row * (TOP_MARGIN + IMG_SIZE);

        boolean overHeight = row == maxRow;


        if (!overHeight) {
            imageView.setLayoutParams(params);
            sliderLayout.addView(imageView);
            customSlideEnter(imageView, params.leftMargin, params.topMargin);
            setCustomSlidableChild(imageView);
            if (overWidth) {
                row++;
            }
        } else {
            Toast.makeText(CustomActivity.this, "Over height", Toast.LENGTH_SHORT).show();
        }

    }

    private void customSlideEnter(View view, int leftMargin, int topMargin) {
        sliderLayout.getConfig().setFinishUi(false);
        sliderLayout.getConfig().setSlideEnter(false);
        int[] enterTarget = sliderLayout.getConfig().getPosition().getEnterTarget(view, sliderLayout.getWidth(), sliderLayout.getHeight());
        sliderLayout.getViewDragHelper().smoothSlideViewTo(view, enterTarget[0] + leftMargin, enterTarget[1] + topMargin, leftMargin, topMargin);
        sliderLayout.getConfig().setFinishUi(true);
    }


    public void setCustomSlidableChild(ImageView customSlidableChild) {
        if(sliderLayout.getConfig().getSlidableMode()==SlidableMode.CUSTOM)
        {

            boolean add=DemoUtils.getRandomBoolean();
            Log.i("KOMI","--------customSlidableChild:"+customSlidableChild.hashCode()+"----add:"+add);

            if(add) {
                sliderLayout.getConfig().getSlidableMode().addCustomSlidableChild(customSlidableChild);
            }
        }
    }
}
