package com.qianfeng.yyz.myswift.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class MyViewPager extends ViewPager{
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (true){
            Log.e("===========","onTouchEvent"+getCurrentItem());
            ViewParent parent = getParent().getParent().getParent().getParent();

            Log.e("==========="," "+ (parent instanceof SlidingPaneLayout));
            parent.requestDisallowInterceptTouchEvent(true);
        }
//       true else {
//            Log.e("===========","=========="+getCurrentItem());
//            getParent().requestDisallowInterceptTouchEvent(false);
////        }
       return super.onTouchEvent(ev);
    }

}
