package com.qianfeng.yyz.myswift.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.adapter.gift.MyGiftListAdapter;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.MyGiftBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpLoginUtils;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.qianfeng.yyz.myswift.mvp.view.SignInActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGiftActivity extends AppCompatActivity implements BeanCallBack {

    @BindView(R.id.my_gift_rv)
    RecyclerView mRecyclerView;
    private List<MyGiftBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE);
        String uid = sp.getString("uid", "1");
        HttpUtils.getInstance().getMyGift(String.format(MyApi.GiftApi.MYGIFT, uid), this, this);
    }

    @Override
    public <T> void callback(T t) {
        mList = (List<MyGiftBean>) t;
        initView();
    }

    private void initView() {
        MyGiftAdapter adapter = new MyGiftAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }

    class MyGiftAdapter extends RecyclerView.Adapter<MyGiftAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyGiftActivity.this).inflate(R.layout.my_gift_item,null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MyGiftBean bean = mList.get(position);
            Picasso.with(MyGiftActivity.this).load(MyApi.BASE_URL+bean.getIconurl()).placeholder(R.mipmap.def_loading).into(holder.mImageView);
            holder.mTime.setText(bean.getOvertime());
            holder.mName.setText(bean.getUname()+"-"+bean.getGiftname());
            holder.mCode.setText(bean.getCodes());
            holder.mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager manger = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    manger.setText(bean.getCodes());
                    Toast.makeText(MyGiftActivity.this,getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.my_gift_img)
            ImageView mImageView;
            @BindView(R.id.my_gift_name)
            TextView mName;
            @BindView(R.id.my_gift_time)
            TextView mTime;
            @BindView(R.id.my_gift_code)
            TextView mCode;
            @BindView(R.id.my_gift_btn)
            Button mBtn;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }


}
