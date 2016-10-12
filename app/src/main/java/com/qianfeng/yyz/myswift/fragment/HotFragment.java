package com.qianfeng.yyz.myswift.fragment;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.hot.MyHotBottomAdapter;
import com.qianfeng.yyz.myswift.adapter.hot.MyHotTopAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.HotBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements BeanCallBack{

    @BindView(R.id.hot_top_rv)
    RecyclerView mTopRecyclerView;
    @BindView(R.id.hot_bottom_rv)
    RecyclerView mBottomRecyclerView;
    private HotBean mHotBean;

    private MyHotTopAdapter mMyHotTopAdapter;
    private MyHotBottomAdapter mMyHotBottomAdapter;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this,view);
        mMyHotBottomAdapter = new MyHotBottomAdapter(getActivity());
        mMyHotTopAdapter = new MyHotTopAdapter(getActivity());
        loadData();
        return view;
    }

    private void loadData() {
        if (null==mHotBean){
            HttpUtils.getInstance().getBean(MyApi.Hote.HOT_LIST,new HotBean(),this,getActivity());
        }
    }

    private void initView() {
        //top
        mMyHotTopAdapter.setmInfobean(mHotBean.getInfo());
        mTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTopRecyclerView.setAdapter(mMyHotTopAdapter);
        //bottom
        mMyHotBottomAdapter.setmInfobean(mHotBean.getInfo());
        mBottomRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mBottomRecyclerView.setAdapter(mMyHotBottomAdapter);
    }

    @Override
    public <T> void callback(T t) {
        mHotBean = (HotBean) t;
        initView();
    }
}
