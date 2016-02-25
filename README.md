
##[中文说明](https://github.com/KobeKomi/Slider/blob/master/README_CN.md)

DESCRIPTION
=================================== 
1.The following description from Google Translation,my english is poor!<br />  
2.Slider purpose is to make the activity, fragment, view with sliding function <br />  
3.Slider in this version by simply extends or xml layout or added to by SliderUtils class with sliding function<br/>  

![Slider](images/s1.gif "Gif Example 1") 
![Slider](images/s2.gif "Gif Example 2")  
![Slider](images/s3.gif "Gif Example 3")  


## USAGE

1.Currently supported activity, fragment, view.<br />  

2.By following simple operation, you can make the page with sliding function.

```java
public class SampleActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        SliderUtils.attachActivity(this, mConfig);
    }
}
```

or by extends:

```java
public class ExtendsActivity extends SliderActivity {
}
```
or by xml:
```xml
    <com.komi.slider.Slider
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:slider="http://schemas.android.com/apk/res-auto"
        android:id="@+id/xml_slider_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        slider:edgeOnly="false"
        slider:position="all">
        
     <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="I CAN SLIDE NOW!"
        android:textSize="23sp" />
        
        </com.komi.slider.Slider>
```



Note: activity of manifest need to override the configured theme: android: windowIsTranslucent, otherwise slide open the background is black.

```xml
 <style name="ActivityTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="android:windowIsTranslucent">true</item>
    </style>
```
If the device is greater than the version of android 5.0, you can also use the following code to solve. I now do not know how the device in less than 5.0 version use code to solve, so this also hope that we can work together to improve!

```java
SliderUtils.attachUi(this, null);
Utils.convertActivityToTranslucent activity)
```

Slide function and view the fragment is not set android:windowIsTranslucent

```java
public class SampleFragment extends Fragment 
{  
    private ISlider iSlider;
    private View rootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        SliderUtils.attachFragment(this, rootView,null)
        return iSlider.getSliderView();
        }
}
```
ragment by extends, you need to pay attention to is: Slider Fragment of onCreateView final because they have been overwritten, 
so another abstract methods to achieve creatingView and returns the current layout of inflate objects.

```java
public class ExtendsFragment extends SliderFragment
{
    @Override
    public View creatingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entends, container, false);
    }
}
```


### SliderConfig、ISlider、SliderListener、SlidableMode

1.If you want to change the properties of Slider, you can get ISlider to set up, or to get to the Slider by directly modifying the xml property you want<br />  

```java
iSlider.setConfig(mConfig);
```
2.ISlider SliderUtils.attach * interface object is returned by the method by which the interface can be operated or obtained Slider object.<br />  

3.SliderListener can pass parameters from the Slider objects or SliderConfig, for listener sliding state.<br />  

4.SlidableMode: ALL: slider children can slide, custom: set the child can slide, single: you can slide in the last position of the child<br />  

5.For more details, please refer to the code<br />  

### DEPENDENCIES

```java
dependencies{
    compile 'com.komi.slider:slider:0.2.0'
}
```

### ISSUES

1.Welcome to the Issues and refine the project, if you like, please click on the star, later will expand more useful features!<br /> 
2.This project extends from[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)and[Slidr](https://github.com/r0adkll/Slidr)<br />  

## AUTHOR
 **[KobeKomi](https://github.com/KobeKomi)** 