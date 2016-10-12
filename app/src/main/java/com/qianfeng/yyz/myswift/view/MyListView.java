package com.qianfeng.yyz.myswift.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import butterknife.OnTouch;


/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class MyListView extends RecyclerView  implements AbsListView.OnScrollListener,View.OnTouchListener{

    private OnRefreshListener mOnrefreshListener;
    private static final int MININSTANCE = 10;
    private double mCurrentInstance;
    private boolean mIsBottom = false;
    private boolean mIsTop = false;
    private double mY;
    private boolean mRefresh = false;


    public MyListView(Context context) {
        super(context);
        initView();
    }


    public MyListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem==0){
            mIsTop = true;
        }
        if (firstVisibleItem+visibleItemCount==totalItemCount){
            mIsBottom = true;
        }
        if (firstVisibleItem!=0){
            mIsTop = false;
        }
        if (firstVisibleItem+visibleItemCount!=totalItemCount){
            mIsBottom = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentInstance = getY()-mY;
                if (mIsTop&&mCurrentInstance>MININSTANCE){
                    initTop();
                    mRefresh = true;
                }
                if (mIsBottom&&mCurrentInstance>MININSTANCE){
                    initBottom();
                    mRefresh  = true;
                }
                mRefresh = false;
                mY = getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mRefresh){
                    if (mOnrefreshListener!=null)
                    mOnrefreshListener.refreshCallback();
                }
                break;

        }
        return false;
    }

    private void initBottom() {
    }

    private void initTop() {
    }

    private void initView() {
    }

    public interface OnRefreshListener{
        void refreshCallback();
    }

    public void setmOnrefreshListener(OnRefreshListener mOnrefreshListener) {
        this.mOnrefreshListener = mOnrefreshListener;
    }
}
