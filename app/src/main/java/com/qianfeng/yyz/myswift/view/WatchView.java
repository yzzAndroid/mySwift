package com.qianfeng.yyz.myswift.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class WatchView extends LinearLayout {
    private float size;
    private TextView timeTextView;
    private ImageView watchImageView;
    private String time;
    private int timeColor;


    public WatchView(Context context) {
        super(context);
    }

    public WatchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //得到自定义的属性
        TypedArray  array = context.obtainStyledAttributes(attrs,R.styleable.WatchView);
        time = array.getString(R.styleable.WatchView_time);
        size = array.getDimension(R.styleable.WatchView_msize,14);
        timeColor = array.getColor(R.styleable.WatchView_time_color,getResources().getColor(R.color.head_tab));
        intiView(context);
    }

    private void intiView(Context context) {
        timeTextView = new TextView(context);
        watchImageView = new ImageView(context);
        //初始化
        watchImageView.setImageResource(R.mipmap.g_biao);
        watchImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        timeTextView.setTextColor(timeColor);
        timeTextView.setText(time);
        timeTextView.setTextSize(size);
        setGravity(Gravity.CENTER_VERTICAL);

        setOrientation(HORIZONTAL);
        addView(watchImageView);
        addView(timeTextView);
    }

    public void setTime(String time){
        timeTextView.setText(time);
    }

}
