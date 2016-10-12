package com.qianfeng.yyz.myswift.adapter.gift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.SearchActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.GitListBean;
import com.qianfeng.yyz.myswift.fragment.GiftFragment;
import com.qianfeng.yyz.myswift.fragment.second.SecondGiftFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class MyGiftListAdapter extends BaseAdapter {

    Context context;
    List<GitListBean.ListBean> path;
    GiftFragment.MyClickListener myClickListener;
    SearchActivity.MyClickListener myClickListener2;
    int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    public MyGiftListAdapter(Context context, List<GitListBean.ListBean> path) {
        this.context = context;
        this.path = path;
    }

    public void setMyClickListener(GiftFragment.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setMyClickListener(SearchActivity.MyClickListener myClickListener2) {
        this.myClickListener2 = myClickListener2;
    }


    @Override
    public int getCount() {
        return path == null ? 0 : path.size();
    }

    @Override
    public Object getItem(int position) {
        return path == null ? null : path.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gift_list_item, null);

            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(MyApi.BASE_URL + path.get(position).getIconurl()).placeholder(R.mipmap.def_loading).into(viewHolder.imageView);
        viewHolder.appName.setText(path.get(position).getGname());
        viewHolder.type.setText(path.get(position).getGiftname());
        viewHolder.time.setText(path.get(position).getAddtime());
        viewHolder.num.setText(String.valueOf(path.get(position).getNumber()));
        if (path.get(position).getNumber() <= 0) {
            viewHolder.mBtn.setBackgroundResource(R.color.gift_less);
            viewHolder.mBtn.setText(context.getString(R.string.less_gift));
        } else {
            viewHolder.mBtn.setBackgroundResource(R.color.head_tab);
            viewHolder.mBtn.setText(context.getString(R.string.gift_button_text));
        }
        viewHolder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    //回调接口
                    myClickListener.setOnClickListener(position);
                } else {
                    myClickListener2.setOnClickListener(position);
                }

            }
        });
        return convertView;
    }

    class ViewHolder {
        View view;
        @BindView(R.id.gift_item_img)
        ImageView imageView;
        @BindView(R.id.gift_item_app_name)
        TextView appName;
        @BindView(R.id.gift_item_type)
        TextView type;
        @BindView(R.id.gift_item_time)
        TextView time;
        @BindView(R.id.gift_item_app_num_real)
        TextView num;
        @BindView(R.id.gift_item_btn)
        Button mBtn;

        public ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
