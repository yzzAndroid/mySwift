package com.qianfeng.yyz.myswift.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/10/4 0004.
 */

public abstract class BaseFragment extends Fragment{

    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            onUnVisible();
        }
    }

    protected  void onUnVisible(){
    }

    protected abstract void lazyLoad();

    protected  void onVisible(){
        lazyLoad();
    }
}
