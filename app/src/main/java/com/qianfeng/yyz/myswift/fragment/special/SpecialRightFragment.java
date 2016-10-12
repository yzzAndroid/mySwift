package com.qianfeng.yyz.myswift.fragment.special;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.special.MySpecialRightAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.SpecialRight;
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
public class SpecialRightFragment extends BaseFragment implements BeanCallBack{

    private boolean isPrepare =false;
    private SpecialRight mSpecialRight;
    private List<SpecialRight.ListBean> mListBean;
    private MySpecialRightAdapter mMySpecialRightAdapter;
    private boolean isFirst = true;
    @BindView(R.id.special_right_rv)
    RecyclerView mRecycleView;
    public SpecialRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_right, container, false);
        ButterKnife.bind(this,view);
        mListBean = new ArrayList<>();
        mMySpecialRightAdapter = new MySpecialRightAdapter(getActivity());
        isPrepare = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepare||!isVisible){
            return;
        }
        //网络请求
        if (null==mSpecialRight){
            HttpUtils.getInstance().getBean(MyApi.Special.SP_RIGHT,new SpecialRight(),this,getActivity());
        }
    }

    @Override
    public <T> void callback(T t) {
        mSpecialRight = (SpecialRight) t;
        mListBean.addAll(mSpecialRight.getList());
        if (isFirst){
            initView();
            isFirst = false;
        }else {
            mMySpecialRightAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mMySpecialRightAdapter.setmList(mListBean);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mMySpecialRightAdapter);
    }
}
