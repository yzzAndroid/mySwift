package com.qianfeng.yyz.myswift.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class MyGrideView extends GridView {
    public MyGrideView(Context context) {
        super(context);
    }

    public MyGrideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGrideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
