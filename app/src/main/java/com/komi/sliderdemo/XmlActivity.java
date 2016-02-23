package com.komi.sliderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class XmlActivity extends AppCompatActivity implements View.OnClickListener {

    private Slider sliderLayout;
    private final int IMG_SIZE = 250;
    private final int LEFT_MARGIN = 20;
    private final int TOP_MARGIN = 30;
    private int row = 0;
    private int column = 0;
    private ISlider iSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        setTitle(this.getClass().getSimpleName());
        sliderLayout = (Slider) findViewById(R.id.slider_layout);
    }


    @Override
    public void onClick(View v) {

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
            case R.id.menu_del:
                if(iSlider!=null) {
                    iSlider.autoExit();
                }
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

        params.topMargin = row * (TOP_MARGIN + IMG_SIZE);

        boolean overHeight = row == maxRow;

       // Log.i("KOMI", "column:" + column + "-----row:" + row + "-----overWidth:" + overWidth + "---overHeight:" + overHeight);

        if (!overHeight) {
            imageView.setLayoutParams(params);
            iSlider=SliderUtils.attachView(sliderLayout,imageView,null);
            customSlideEnter(imageView,sliderLayout.getWidth(),params.topMargin,params.leftMargin,params.topMargin);
            if (overWidth) {
                row++;
            }
        } else {
            Toast.makeText(XmlActivity.this, "Over Slider", Toast.LENGTH_SHORT).show();
        }

    }

    private void customSlideEnter(View view,int startLeft,int startTop,int finalLeft,int finalTop) {
        iSlider.getConfig().setFinishUi(false);
        iSlider.getConfig().setSlideEnter(false);
        sliderLayout.getViewDragHelper().smoothSlideViewTo(view, startLeft, startTop, finalLeft, finalTop);
        iSlider.getConfig().setFinishUi(true);
    }



}
