package com.komi.sliderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.komi.slider.Slider;
import com.komi.slider.SliderListener;

public class XmlActivity extends AppCompatActivity implements View.OnClickListener {

    private Slider sliderLayout;
    private final int IMG_SIZE = 250;
    private final int LEFT_MARGIN = 20;
    private final int TOP_MARGIN = 30;
    private int row = 0;
    private int column = 0;
    private int maxColumn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        setTitle(this.getClass().getSimpleName());
        sliderLayout = (Slider) findViewById(R.id.slider_layout);
        sliderLayout.setOnPanelSlideListener(new SliderListener() {
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
            public void onSlideClosed(View slidableChild) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.xml_fab);
//        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.xml_fab:
//
//                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_xml, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addSliderChild();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void addSliderChild() {
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


        params.topMargin = row * (LEFT_MARGIN + IMG_SIZE);


        boolean overHeight = row == maxRow;

        Log.i("KOMI", "column:" + column + "-----row:" + row + "-----overWidth:" + overWidth + "---overHeight:" + overHeight);

        if (!overHeight) {
            imageView.setLayoutParams(params);
            sliderLayout.addView(imageView);
            sliderLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            sliderLayout.slideEnter();
                        }
                    }
            );
            if (overWidth) {
                row++;
            }
        } else {
            Toast.makeText(XmlActivity.this, "Over Slider", Toast.LENGTH_SHORT).show();
        }

    }

}
