package com.qianfeng.yyz.myswift.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.MainActivity;
import com.qianfeng.yyz.myswift.activity.SecondActivity;
import com.qianfeng.yyz.myswift.adapter.gift.MyGiftListAdapter;
import com.qianfeng.yyz.myswift.adapter.pager.ViewPagerAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.GitListBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.qianfeng.yyz.myswift.utils.ViewPagerAutoPlay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftFragment extends Fragment implements BeanCallBack{

    private   boolean isFirst = true;
    private List<GitListBean.ListBean> mGitListBean;
    private GitListBean mGitList;
    private int mCurrentPosition = 1;
    private ViewPager mViewPager;
    private LinearLayout mPoint;
    private int mCurrentItem = 0;

    @BindView(R.id.pbr)
    ProgressBar mProgressBar;

    PullToRefreshListView mListView;
    private MyGiftListAdapter mMyGiftListAdapter;
    private ViewPagerAdapter mViewPagerAdapter;
    private float mOriganposition = 1;
    private static long lastUpDateTime;

    public GiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        ButterKnife.bind(this,view);

        RelativeLayout mRelativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.gift_view_player_item, null);
        mViewPager = (ViewPager) mRelativeLayout.findViewById(R.id.gift_view_pager);
        mPoint = (LinearLayout) mRelativeLayout.findViewById(R.id.gift_view_pager_point);
        mListView = (PullToRefreshListView) view.findViewById(R.id.gift_fragment_lv);
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1));
        mGitListBean = new ArrayList<>();
        mListView.getRefreshableView().addHeaderView(mRelativeLayout);
        mViewPagerAdapter = new ViewPagerAdapter();
        //二级跳转监听
        initClick();
        lazy();

        return view;
    }




    private void lazy() {
        //加载数据
        if (mGitList==null){
            HttpUtils.getInstance().getBean(String.format(MyApi.GiftApi.GIFT_LIST, mCurrentPosition), new GitListBean(), this,getActivity());
        }


    }


    @Override
    public <T> void callback(T t) {
        if (t instanceof GitListBean) {
            mGitList = (GitListBean) t;
            mCurrentItem = mGitListBean.size();
            mGitListBean.addAll(mGitList.getList());
            if (isFirst){
                initViewPlayer();
                initList();
                ViewPagerAutoPlay.startPlay();
                lastUpDateTime = System.currentTimeMillis();
                isFirst = false;
            }else {

                mListView.getRefreshableView().setSelection(mCurrentItem);
                mMyGiftListAdapter.notifyDataSetChanged();
                initViewPlayer();
            }

        }
    }


    private void initViewPlayer() {
        List<String> mListPath = new ArrayList<>();
        ArrayList<View> viewList = new ArrayList<>();
        List<GitListBean.AdBean> mListAdBean = mGitList.getAd();
        for (GitListBean.AdBean mAdBean : mListAdBean) {
            mListPath.add(MyApi.BASE_URL + mAdBean.getIconurl());
        }
        //再添加一张
        mListPath.add(mListPath.get(0));
        for (String s : mListPath) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(imageView);
        }


        mViewPagerAdapter.setContext(getActivity());
        mViewPagerAdapter.setPath(mListPath);
        mViewPagerAdapter.setViewList(viewList);
        mViewPager.setAdapter(mViewPagerAdapter);

        //请求轮播
        if (isFirst){
            ViewPagerAutoPlay.initPoint(mPoint, mViewPager, mListPath, getActivity(), this);
        }
    }

    private void initList() {

        mMyGiftListAdapter = new MyGiftListAdapter(getActivity(), mGitListBean);
        //加入监听接口
        mMyGiftListAdapter.setMyClickListener(getMyClickListener());
        mListView.setAdapter(mMyGiftListAdapter);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING){
                    if (view.getFirstVisiblePosition()==0){
                        mCurrentItem = 0;
                    }else {
                        mCurrentItem = view.getLastVisiblePosition();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //上拉
                ILoadingLayout proxy = mListView.getLoadingLayoutProxy();
                if (mOriganposition<firstVisibleItem){
                    Log.e("==========","======onScroll====1=");
                    proxy.setLoadingDrawable(getResources().getDrawable(R.color.white));
                    proxy.setReleaseLabel("松开载入更多");
                    proxy.setPullLabel("查看更多");
                    proxy.setLastUpdatedLabel("");

                    //下拉
                }else if (mOriganposition>firstVisibleItem){
                    Log.e("==========","======onScroll===2==");
                    proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.default_ptr_flip));
                    proxy.setPullLabel("下拉刷新");
                    proxy.setReleaseLabel("松开刷新数据");
                    lastUpDateTime(proxy);
                    mListView.setShowViewWhileRefreshing(true);
                }
                mOriganposition = firstVisibleItem;
            }
        });
       mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
           @Override
           public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               refesh();
               complete();
           }

           @Override
           public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

               refesh();
               complete();
           }
       });
    }

    private void complete() {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.onRefreshComplete();
            }
        },1000);
    }


    public void refesh() {
        mCurrentPosition += 1;
        HttpUtils.getInstance().getBean(String.format(MyApi.GiftApi.GIFT_LIST, mCurrentPosition), new GitListBean(), this,getActivity());
    }

    //上次更新时间
    public void lastUpDateTime(ILoadingLayout proxy){

        long time = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String formateTime = simpleDateFormat.format(time);
        proxy.setLastUpdatedLabel("上次更新时间："+formateTime);
        lastUpDateTime = time;
    }

    public void initClick(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //加了头，要-2(一个是刷新，一个是ViewPager)
               getMyClickListener().setOnClickListener(position-2);
            }
        });
    }


    public MyClickListener getMyClickListener() {
       return new MyClickListener() {
           @Override
           public void setOnClickListener(int position) {
               Intent intent = new Intent(getActivity(), SecondActivity.class);
               String gId = mGitListBean.get(position).getId();
               intent.putExtra("id",gId);
               intent.putExtra("type",1);
               intent.putExtra("name",mGitListBean.get(position).getGname()+"-"+mGitListBean.get(position).getGiftname());
               startActivity(intent);
           }
       };
    }

    public interface MyClickListener{
        void setOnClickListener(int position);
    }

    

}


