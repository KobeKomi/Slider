package com.komi.sliderdemo;

import java.util.Random;

/**
 * Created by Komi on 2016-02-22.
 */
public class DemoUtils {
    private static DemoUtils ourInstance = new DemoUtils();

    public static DemoUtils getInstance() {
        return ourInstance;
    }

    private DemoUtils() {
    }

    public static final int[] PIC_ARRAY_RES = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5, R.mipmap.pic6, R.mipmap.pic7, R.mipmap.pic8, R.mipmap.pic9};
    public static Random random=new Random();

    public static int getRandomPicRes()
    {
        int index=random.nextInt(PIC_ARRAY_RES.length-1);
        return PIC_ARRAY_RES[index];
    }
}
