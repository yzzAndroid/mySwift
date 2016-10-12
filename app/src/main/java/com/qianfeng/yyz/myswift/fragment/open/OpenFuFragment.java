package com.qianfeng.yyz.myswift.fragment.open;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.open.OpenFuAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.OpenFuBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFuFragment extends Fragment implements BeanCallBack{
    @BindView(R.id.open_fu_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.open_fu_progressBar)
    ProgressBar mProgressBar;

    private boolean isVisable = false;
    private boolean isInited = false;
    private OpenFuAdapter mAdapter;

    private OpenFuBean mOpenFuBean;

    public OpenFuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open_scroller, container, false);
        ButterKnife.bind(this,view);
        //执行懒加载
        isInited = true;
        lazy();

        return view;
    }


    private void lazy() {
        if (isVisable&&isInited){
            if (null==mOpenFuBean){
                HttpUtils.getInstance().getBean(MyApi.Open.OPEN_FU,new OpenFuBean(),this,getActivity());
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

    private void intiViews() {

        //数据的类型
       List<Integer> ltype = new ArrayList<>();
        //最后传进去的数据
       List<OpenFuBean.InfoBean> allListInfo = new ArrayList<>();
        if (mOpenFuBean!=null&&mOpenFuBean.getInfo().size()!=0){
            //得到实体list集合
            List<OpenFuBean.InfoBean> listInfo= mOpenFuBean.getInfo();

            int size = listInfo.size();
            String addTime = "";
            for (int i = 0;i<size;i++){

                if (!addTime.equals(listInfo.get(i).getAddtime())){

                    addTime = listInfo.get(i).getAddtime();
                    //天假布局1
                    ltype.add(1);
                    allListInfo.add(listInfo.get(i));
                }
                //添加布局2
                ltype.add(2);
                allListInfo.add(listInfo.get(i));
            }
        }

        mAdapter = new OpenFuAdapter(getActivity());
        mAdapter.setType(ltype);
        mAdapter.setAllList(allListInfo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public <T> void callback(T t) {
        mOpenFuBean = (OpenFuBean) t;
         mProgressBar.setVisibility(View.GONE);
         intiViews();
    }
}
