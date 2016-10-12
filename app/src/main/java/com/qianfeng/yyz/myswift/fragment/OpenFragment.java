package com.qianfeng.yyz.myswift.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.fragment.open.OpenCeFragment;
import com.qianfeng.yyz.myswift.fragment.open.OpenFuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends Fragment {

    @BindView(R.id.open_tab_layout)
    TabLayout mTableLayout;
    @BindView(R.id.open_vp)
    ViewPager mViewPager;
    List<String> mTitleList;

    ArrayList<Fragment> fragmentList;

    public OpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_open, container, false);
        ButterKnife.bind(this,view);
        initTab();
        initVp();
        mTableLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void initVp() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new OpenFuFragment());
        fragmentList.add(new OpenCeFragment());
        mViewPager.setAdapter(new MyAdapter(getFragmentManager()));
    }

    private void initTab() {

        mTitleList = new ArrayList<>();
        mTitleList.add(getString(R.string.open_fu));
        mTitleList.add(getString(R.string.open_ce));
        mTableLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.head_tab));
        mTableLayout.setTabTextColors(R.color.head_tab,R.color.head_tab);


    }

    //适配器
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

}
