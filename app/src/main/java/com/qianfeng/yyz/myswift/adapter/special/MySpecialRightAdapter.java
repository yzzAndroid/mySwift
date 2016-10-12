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
import com.qianfeng.yyz.myswift.activity.SpecialRightDetalActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.SpecialLeft;
import com.qianfeng.yyz.myswift.bean.SpecialRight;
import com.qianfeng.yyz.myswift.view.WatchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/10/4 0004.
 */
public class MySpecialRightAdapter extends RecyclerView.Adapter<MySpecialRightAdapter.MyRightViewHolder> {

    private List<SpecialRight.ListBean> mList;
    private Context context;

    public MySpecialRightAdapter(Context context) {
        this.context = context;
    }

    public void setmList(List<SpecialRight.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public MyRightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sepcial_right_rv_item,null);
        return new MyRightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRightViewHolder holder, int position) {
        SpecialRight.ListBean listBean = mList.get(position);
        Picasso.with(context).load(MyApi.BASE_URL+listBean.getIconurl())
                .resize(600,300)
                .placeholder(R.mipmap.def_loading).into(holder.mImageView);
        holder.mName.setText(listBean.getName());

        Picasso.with(context).load(MyApi.BASE_URL+"/"+listBean.getAuthorimg()).placeholder(R.mipmap.def_loading)
                .into(holder.mIcon);
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class MyRightViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.special_right_item_img)
        ImageView mImageView;
        @BindView(R.id.special_right_item_name)
        TextView mName;
        @BindView(R.id.special_right_item_icon)
        CircleImageView mIcon;
        public MyRightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, SpecialRightDetalActivity.class);
                    intent.putExtra("path",String.format(MyApi.Special.SP_RIGHT_DT,mList.get(position).getId()));
                    intent.putExtra("uri",mList.get(position).getIconurl());
                    intent.putExtra("read",mList.get(position).getDescs());
                    intent.putExtra("name",mList.get(position).getName());
                    intent.putExtra("author",mList.get(position).getAuthorimg());
                    context.startActivity(intent);
                }
            });
        }
    }
}
