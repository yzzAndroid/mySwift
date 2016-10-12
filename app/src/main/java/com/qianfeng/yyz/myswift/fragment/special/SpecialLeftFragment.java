package com.qianfeng.yyz.myswift.fragment.special;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.special.MySpecialLeftAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.HotBean;
import com.qianfeng.yyz.myswift.bean.SpecialLeft;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.fragment.BaseFragment;
import com.qianfeng.yyz.myswift.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialLeftFragment extends BaseFragment implements BeanCallBack {

    private boolean isPrepare =false;
    private MySpecialLeftAdapter mMySpecialLeftAdapter;
    private SpecialLeft mSpecialLeft;
    private List<SpecialLeft.ListBean> mListBean;
    private boolean isFirst = true;

    @BindView(R.id.special_left_rv)
    RecyclerView mRecyclerView;

    public SpecialLeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_left, container, false);
        ButterKnife.bind(this,view);
        //初始化字段
        mMySpecialLeftAdapter = new MySpecialLeftAdapter(getActivity());
        mListBean = new ArrayList<>();
        isPrepare = true;
        lazyLoad();
        return view;
    }


   @Override
    public void lazyLoad(){
        if (!isPrepare||!isVisible){
            return;
        }
        //网络请求
       if (mSpecialLeft==null){
           HttpUtils.getInstance().getBean(MyApi.Special.SP_LEFT,new SpecialLeft(),this,getActivity());
       }
    }


    @Override
    public <T> void callback(T t) {
        mSpecialLeft = (SpecialLeft) t;
        mListBean.addAll(mSpecialLeft.getList());
        if (isFirst){
            initView();
            isFirst = false;
        }else {
            mMySpecialLeftAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mMySpecialLeftAdapter.setmList(mListBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMySpecialLeftAdapter);


    }


}
