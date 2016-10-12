package com.qianfeng.yyz.myswift.adapter.open;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.SecondActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.OpenCeBan;
import com.qianfeng.yyz.myswift.view.WatchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class MyOpenCeAdapter extends RecyclerView.Adapter<MyOpenCeAdapter.MyOpenCeViewHolder> {

    private Context mContext;
    private List<OpenCeBan.InfoBean> mInfoBeen;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setmInfoBeen(List<OpenCeBan.InfoBean> mInfoBeen) {
        this.mInfoBeen = mInfoBeen;
    }

    public MyOpenCeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MyOpenCeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.opence_item,null);
        return new MyOpenCeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOpenCeViewHolder holder, final int position) {
        Picasso.with(mContext).load(MyApi.BASE_URL+mInfoBeen.get(position).getIconurl()).placeholder(R.mipmap.def_loading).into(holder.imageView);
        holder.nameTv.setText(mInfoBeen.get(position).getGname());
        holder.timeTv.setTime(mInfoBeen.get(position).getTeststarttime());
        holder.netName.setText(mInfoBeen.get(position).getOperators());
        holder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfoBeen.size();
    }

    class MyOpenCeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.opence_item_img)
        ImageView imageView;
        @BindView(R.id.opence_item_appName)
        TextView nameTv;
        @BindView(R.id.opence_item_net_name)
        TextView netName;
        @BindView(R.id.opence_item_time)
        WatchView timeTv;
        @BindView(R.id.opence_button_check)
        Button mCheck;

        public MyOpenCeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    initData(position);
                }
            });
        }
    }
    public void initData(int position){
        //实现跳转
        Log.e("=======","======"+position);
        Intent intent = new Intent(mContext, SecondActivity.class);
        String gId = mInfoBeen.get(position).getGid();
        intent.putExtra("id",gId);
        intent.putExtra("type",3);
        intent.putExtra("name",mInfoBeen.get(position).getGname());
        mContext.startActivity(intent);
    }
}
