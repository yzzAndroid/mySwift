package com.qianfeng.yyz.myswift.adapter.special;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class MySpecialAdapter extends FragmentPagerAdapter{

    private List<String> mTitle;
    private List<Fragment> mFragmentView;
    public MySpecialAdapter(FragmentManager fm,List<String> mTitle,List<Fragment> mFragmentView) {
        super(fm);
        this.mTitle = mTitle;
        this.mFragmentView = mFragmentView;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentView.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentView.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
