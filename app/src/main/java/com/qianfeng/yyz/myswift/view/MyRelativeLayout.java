package com.qianfeng.yyz.myswift.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class MyRelativeLayout extends RelativeLayout {
    private SlidingPaneLayout slidingPaneLayout;

    public MyRelativeLayout(Context context) {
        this(context,null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setSlidingPaneLayout(SlidingPaneLayout slidingPaneLayout) {
        this.slidingPaneLayout = slidingPaneLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (slidingPaneLayout!=null&&slidingPaneLayout.isOpen()){
            Log.e("=============","============拦截==");
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (slidingPaneLayout.isOpen()&&event.getAction()==MotionEvent.ACTION_DOWN){
            return true;
        }
        if (slidingPaneLayout.isOpen()&&event.getAction()==MotionEvent.ACTION_UP){
            slidingPaneLayout.closePane();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
