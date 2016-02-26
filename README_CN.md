Slider  说明
=================================== 
1.slider重在让activity、fragment、view具备滑动功能操作<br/>
2.slider在此版本只需通过简单的继承、xml布局或者直接将上面3种类型对象通过SliderUtils类即可具备滑动功能<br/>
![Slider](images/s1.gif "Gif Example 1")
![Slider](images/s2.gif "Gif Example 2")
![Slider](images/s3.gif "Gif Example 3")


## 使用方法

1.目前支持activity、fragment、view的滑动<br/>

2.通过如下简单操作，即可让页面具备滑动功能。<br/>

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

亦或通过简单的继承:

```java
public class ExtendsActivity extends SliderActivity {
}
```
或者是直接通过xml布局，就可以让子View具备滑动功能
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


需要注意的是activity在manifest下配置theme需复写android:windowIsTranslucent，否则拉开的背景会全黑。
```xml
 <style name="ActivityTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="android:windowIsTranslucent">true</item>
    </style>
```
如果设备版本大于android 5.0，也可以如下代码。在低于5.0版本下如何能在代码中修改，目前没有深入，所以在此也希望大家能共同完善！
```java
Utils.convertActivityToTranslucent activity)
```

关于fragment和view实现滑动功能，也基本可以通过类似的方式进行。而fragment不受android:windowIsTranslucent的影响。
```java
public class SampleFragment extends Fragment 
{  
    private ISlider iSlider;
    private View rootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        iSlider=SliderUtils.attachFragment(this, rootView,null)
        return iSlider.getSliderView();
        }
}
```
或者通过继承,需要注意的是：在SliderFragment中onCreateView由于已经被final覆写了，所以要实现另一个抽象方法
creatingView并返回当前layout的inflate对象。
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

1.如果你想改变Slider的一些属性，可以在任何地方通过iSlider来设置或者在初始化的时候传入,或者通过xml传入，也可以获得slider控件对象来直接修改你想要的属性，方式多种多样。<br/>
```java
iSlider.setConfig(mConfig);
```
2.ISlider是在SliderUtils.attach*方法返回的接口对象，通过该接口可以操作或者获得Slider对象。<br/>

3.SliderListener可以通过slider对象或者SliderConfig传入,监听滑动状态。<br/>

4.SlidableMode：ALL：所有slider的子控件可滑动，custom:传入的自控件可以滑动，single：index为最后的子控件可滑动<br/>

5.其他更多各位可以参考代码。<br/>

### 使用项目

在项目的build.gradle文件中添加库依赖
```java
dependencies{
    compile 'com.komi.slider:slider:0.2.0'
}
```

### Issues

1.欢迎大家发现问题与共同维护该项目,喜欢就Star吧，关注项目后面会扩展更多实用功能！<br/>
2.如果大家发行有什么Bug或建议都可以通过issues反馈过来，本人有时间会尽量去优化，也愿意和大家一块共同开发此项目！<br/>
3.此项目灵感源于[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)和[Slidr](https://github.com/r0adkll/Slidr)开源项目进行功能的扩展补充<br />  
4.！！！！！！！！在此请求英语大神的帮助翻译README文档和代码注释，非常感谢！<br/>

## 项目作者
 **[KobeKomi](https://github.com/KobeKomi)** 
