package com.qianfeng.yyz.myswift.adapter.special;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.SpecialLeftDetalActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.SpecialLeft;
import com.qianfeng.yyz.myswift.view.WatchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/4 0004.
 */
public class MySpecialLeftAdapter extends RecyclerView.Adapter<MySpecialLeftAdapter.MyLeftViewHolder> {

    private List<SpecialLeft.ListBean> mList;
    private Context context;

    public MySpecialLeftAdapter(Context context) {
        this.context = context;
    }

    public void setmList(List<SpecialLeft.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public MyLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sepcial_left_rv_item,null);
        return new MyLeftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyLeftViewHolder holder, int position) {
        SpecialLeft.ListBean listBean = mList.get(position);
        Picasso.with(context).load(MyApi.BASE_URL+listBean.getIconurl()).
                placeholder(R.mipmap.def_loading)
                .resize(600,300)
                .into(holder.mImageView);
        holder.mName.setText(listBean.getName());
        holder.mTime.setTime(listBean.getAddtime());
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class MyLeftViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.special_left_item_img)
        RoundedImageView mImageView;
        @BindView(R.id.special_left_item_name)
        TextView mName;
        @BindView(R.id.special_left_item_time)
        WatchView mTime;

        public MyLeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SpecialLeftDetalActivity.class);
                    int position = getAdapterPosition();
                    intent.putExtra("path",String.format(MyApi.Special.SP_LEFT_DT,mList.get(position).getSid()));
                    intent.putExtra("uri",mList.get(position).getIconurl());
                    intent.putExtra("read",mList.get(position).getDescs());
                    intent.putExtra("name",mList.get(position).getName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
