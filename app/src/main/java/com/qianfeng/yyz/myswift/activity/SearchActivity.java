package com.qianfeng.yyz.myswift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.gift.MyGiftListAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.GitListBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements BeanCallBack{

    private GitListBean mGitListBean;
    @BindView(R.id.search_rv)
    ListView mListView;
    @BindView(R.id.search_et)
    EditText mEditext;

    private MyGiftListAdapter mMyGiftListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        HttpUtils.getInstance().getBean(MyApi.GiftApi.GIFT_LIST,new GitListBean(),this,this);
    }

    public void back(View view) {
        finish();
    }

    public void doSearch(View view) {
        if(TextUtils.isEmpty(mEditext.getText())){
            Toast.makeText(SearchActivity.this, "输入不正确", Toast.LENGTH_SHORT).show();
        }else {
            HttpUtils.getInstance().getBean(mEditext.getText().toString(),new GitListBean(),this,HttpUtils.POST,this);
        }
    }

    @Override
    public <T> void callback(T t) {
        mGitListBean = (GitListBean) t;
        if (mGitListBean.getList()==null){
            Toast.makeText(SearchActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            return;
        }
        initList();
    }

    private void initList() {
        mMyGiftListAdapter = new MyGiftListAdapter(this,mGitListBean.getList());
        mMyGiftListAdapter.setMyClickListener(getMyClickListener());
        mMyGiftListAdapter.setType(2);
        mListView.setAdapter(mMyGiftListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //加了头，要-2(一个是刷新，一个是ViewPager)
                getMyClickListener().setOnClickListener(position);
            }
        });
    }

    public MyClickListener getMyClickListener() {
        return new MyClickListener() {
            @Override
            public void setOnClickListener(int position) {
                Intent intent = new Intent(SearchActivity.this, SecondActivity.class);
                String gId = mGitListBean.getList().get(position).getId();
                intent.putExtra("id",gId);
                intent.putExtra("type",1);
                intent.putExtra("name",mGitListBean.getList().get(position).getGname()+"-"+mGitListBean.getList().get(position).getGiftname());
                startActivity(intent);
            }
        };
    }

    public interface MyClickListener{
        void setOnClickListener(int position);
    }



}
