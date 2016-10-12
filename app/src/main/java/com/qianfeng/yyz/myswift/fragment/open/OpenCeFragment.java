package com.qianfeng.yyz.myswift.fragment.open;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.open.MyOpenCeAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.OpenCeBan;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenCeFragment extends Fragment implements BeanCallBack{
    
    private boolean isVisable = false;
    private boolean isInit = false;
    
    private OpenCeBan mOpenCeBean;
    private  MyOpenCeAdapter adapter;

    @BindView(R.id.opence_rcv)
    RecyclerView mRecycleView;
    @BindView(R.id.open_ce_progress)
    ProgressBar mProgressBar;
    public OpenCeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_ce, container, false);
        ButterKnife.bind(this,view);
        isInit = true;
        lazy();
        return view;
    }

    private void lazy() {
        if (isVisable&&isInit){
            if(mOpenCeBean==null){
                //网络请求
                HttpUtils.getInstance().getBean(MyApi.Open.OPEN_CE,new OpenCeBan(),this,getActivity());
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisable = true;
            lazy();
        }else {
            isVisable = false;
        }
    }

    @Override
    public <T> void callback(T t) {
        mOpenCeBean = (OpenCeBan) t;
        mProgressBar.setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyOpenCeAdapter(getActivity());
        adapter.setmInfoBeen(mOpenCeBean.getInfo());
        mRecycleView.setAdapter(adapter);
    }
}
