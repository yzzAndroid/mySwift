package com.qianfeng.yyz.myswift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.SpecialRightSecondBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SpecialRightDetalActivity extends AppCompatActivity implements BeanCallBack{

    private String mName;
    private String mUri;
    private String mDesc;
    private String mPath;
    private String mAuthor;
    @BindView(R.id.special_right_lv)
    ListView mLIstView;
    private SpecialRightSecondBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_right_detal);
        Intent intent = getIntent();
        mPath = intent.getStringExtra("path");
        mName = intent.getStringExtra("name");
        mUri = intent.getStringExtra("uri");
        mDesc = intent.getStringExtra("read");
        mAuthor = intent.getStringExtra("author");
        ButterKnife.bind(this);
        HttpUtils.getInstance().getBean(mPath,new SpecialRightSecondBean(),this,this);
    }

    @Override
    public <T> void callback(T t) {
        bean = (SpecialRightSecondBean) t;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(this).inflate(R.layout.special_right_second_head,null);
        ImageView bg = (ImageView) view.findViewById(R.id.special_right_head_bc);
        TextView title = (TextView) view.findViewById(R.id.special_left_second_title);
        RelativeLayout back = (RelativeLayout) view.findViewById(R.id.special_right_head_back);
        RelativeLayout share = (RelativeLayout) view.findViewById(R.id.special_right_head_share);
        CircleImageView icon = (CircleImageView) view.findViewById(R.id.special_right_head_author);
        TextView read = (TextView) view.findViewById(R.id.special_right_head_read);
        Picasso.with(this).load(MyApi.BASE_URL+"/"+mAuthor).placeholder(R.mipmap.def_loading).into(icon);
        Picasso.with(this).load(MyApi.BASE_URL+mUri).placeholder(R.mipmap.def_loading).into(bg);
        title.setText(mName);
        read.setText(mDesc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLIstView.addHeaderView(view);

        Adapter adapter = new Adapter();
        mLIstView.setAdapter(adapter);

        mLIstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    return;
                }
                into(position-1);
            }
        });
    }

    public void into(int position){
        //实现跳转
        Intent intent = new Intent(SpecialRightDetalActivity.this, SecondActivity.class);
        String gId = bean.getList().get(position).getAppid();
        intent.putExtra("id",gId);
        intent.putExtra("type",2);
        intent.putExtra("name",bean.getList().get(position).getAppname());
        startActivity(intent);
    }

    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return bean==null?0:bean.getList().size();
        }

        @Override
        public Object getItem(int position) {
            return bean.getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView = LayoutInflater.from(SpecialRightDetalActivity.this).inflate(R.layout.special_right_second_item,null);
                holder = new ViewHolder(convertView);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            Picasso.with(SpecialRightDetalActivity.this).load(MyApi.BASE_URL+bean.getList().get(position).getIconurl()).placeholder(R.mipmap.def_loading).into(holder.icon);
            holder.name.setText(bean.getList().get(position).getAppname());
            holder.type.setText(bean.getList().get(position).getTypename());
            holder.size.setText(bean.getList().get(position).getAppsize());
            holder.desc.setText(bean.getList().get(position).getDescs());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    into(position);
                }
            });
            return convertView;
        }

        class ViewHolder {
            View view;
            @BindView(R.id.special_second_right_img)
            ImageView icon;
            @BindView(R.id.special_second_right_size)
            TextView size;

            @BindView(R.id.special_second_right_type)
            TextView type;

            @BindView(R.id.special_second_right_desc)
            TextView desc;
            @BindView(R.id.special_second_right_name)
            TextView name;

            @BindView(R.id.special_second_right_bn)
            TextView btn;
            public ViewHolder(View view) {
                this.view = view;
                ButterKnife.bind(this,view);
                view.setTag(this);
            }
        }
    }
}
