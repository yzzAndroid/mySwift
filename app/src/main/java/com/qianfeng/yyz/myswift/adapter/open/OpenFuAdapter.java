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
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.SecondActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.OpenFuBean;
import com.qianfeng.yyz.myswift.view.WatchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class OpenFuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<OpenFuBean.InfoBean> allList;
    private List<Integer> type;


    public void setAllList(List<OpenFuBean.InfoBean> allList) {
        this.allList = allList;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public OpenFuAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        return allList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.openfu_item1, null);
            return new ViewHolderType1(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.openfu_item2, null);
            return new ViewHolderType2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (type.get(position)==1){
            ViewHolderType1 holderType1 = (ViewHolderType1) holder;
            holderType1.watchView.setTime(allList.get(position).getAddtime());
        }else {
            ViewHolderType2 holderType2 = (ViewHolderType2) holder;
            Picasso.with(mContext).load(MyApi.BASE_URL+allList.get(position).getIconurl()).placeholder(R.mipmap.def_loading).into(holderType2.imageView);
            holderType2.nameTv.setText(allList.get(position).getGname());
            holderType2.netName.setText(allList.get(position).getOperators());
            holderType2.watchView.setTime(allList.get(position).getStarttime());
            holderType2.area.setText(allList.get(position).getArea());
            holderType2.mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type.get(position);
    }


    class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.openfu_item1_watch_view)
        WatchView watchView;
        public ViewHolderType1(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.openfu_item2_img)
        ImageView imageView;
        @BindView(R.id.openfu_item2_appName)
        TextView nameTv;
        @BindView(R.id.openfu_item2_time)
        WatchView watchView;
        @BindView(R.id.openfu_item2_net_name)
        TextView netName;
        @BindView(R.id.openfu_item2_arrer)
        TextView area;
        @BindView(R.id.openfu_button_check)
        Button mCheck;
        public ViewHolderType2(final View itemView) {
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
        String gId = allList.get(position).getGid();
        intent.putExtra("id",gId);
        intent.putExtra("type",2);
        intent.putExtra("name",allList.get(position).getGname());
        mContext.startActivity(intent);
    }
}
