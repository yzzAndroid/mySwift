package com.qianfeng.yyz.myswift.adapter.hot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.SecondActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.HotBean;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class MyHotBottomAdapter  extends RecyclerView.Adapter<MyHotBottomAdapter.MyViewHolder>{

    private HotBean.InfoBean mInfobean;
    private Context context;

    public MyHotBottomAdapter(Context context) {
        this.context = context;
    }

    public void setmInfobean(HotBean.InfoBean mInfobean) {
        this.mInfobean = mInfobean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_bottom_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HotBean.InfoBean.Push2Bean push2Bean = mInfobean.getPush2().get(position);
        Picasso.with(context).load(MyApi.BASE_URL+push2Bean.getLogo()).placeholder(R.mipmap.def_loading).into(holder.mImageView);
        holder.mName.setText(push2Bean.getName());
    }

    @Override
    public int getItemCount() {
        return mInfobean.getPush2().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_bottom_item_img)
        ImageView mImageView;
        @BindView(R.id.hot_bottom_item_name)
        TextView mName;
        public MyViewHolder(View itemView) {
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
        Intent intent = new Intent(context, SecondActivity.class);
        String gId = mInfobean.getPush2().get(position).getAppid();
        intent.putExtra("id",gId);
        intent.putExtra("type",4);
        intent.putExtra("name",mInfobean.getPush2().get(position).getName());
        context.startActivity(intent);
    }

}
