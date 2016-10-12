package com.qianfeng.yyz.myswift.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.special.MySpecialAdapter;
import com.qianfeng.yyz.myswift.fragment.special.SpecialLeftFragment;
import com.qianfeng.yyz.myswift.fragment.special.SpecialRightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragment extends Fragment {


    @BindView(R.id.special_vp)
    ViewPager mViewPager;
    @BindView(R.id.special_tab)
    SlidingTabLayout mTabLayout;
    private MySpecialAdapter mMySpecialAdapter;
    private List<Fragment> mFragmentList;
    private List<String> mList;
    public SpecialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special, container, false);
        ButterKnife.bind(this,view);
        mFragmentList = new ArrayList<>();
        mList = new ArrayList<>();
        initView();
        return view;
    }

    private void initView() {

        mFragmentList.add(new SpecialLeftFragment());
        mFragmentList.add(new SpecialRightFragment());
        mList.add(getString(R.string.special_left));
        mList.add(getString(R.string.special_right));

        mMySpecialAdapter = new MySpecialAdapter(getFragmentManager(),mList,mFragmentList);
        mViewPager.setAdapter(mMySpecialAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mViewPager);
    }
}
