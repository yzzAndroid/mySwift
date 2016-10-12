package com.qianfeng.yyz.myswift.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.SpecialLeftSecondBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.qianfeng.yyz.myswift.view.MyGrideView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialLeftDetalActivity extends AppCompatActivity implements BeanCallBack {

    private String mName;
    private String mUri;
    private String mDesc;
    private String mPath;
    private SpecialLeftSecondBean bean;
    @BindView(R.id.special_left_second_img)
    ImageView mIcon;
    @BindView(R.id.special_left_second_read)
    TextView mRead;
    @BindView(R.id.special_left_second_title)
    TextView mTitle;
    @BindView(R.id.special_left_second_giv)
    MyGrideView grideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_left_detal);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mPath = intent.getStringExtra("path");
        mName = intent.getStringExtra("name");
        mUri = intent.getStringExtra("uri");
        mDesc = intent.getStringExtra("read");

        HttpUtils.getInstance().getBean(mPath, new SpecialLeftSecondBean(), this, this);
    }

    public void back(View view) {
        finish();
    }

    public void search(View view) {
    }

    @Override
    public <T> void callback(T t) {
        bean = (SpecialLeftSecondBean) t;
        initViews();
    }

    private void initViews() {
        mTitle.setText(mName);
        Picasso.with(this).load(MyApi.BASE_URL+mUri)
                .placeholder(R.mipmap.def_loading)
                .resize(600,300)
                .into(mIcon);
        mRead.setText(mDesc);
        Adapter adapter= new Adapter();
        grideView.setAdapter(adapter);
        grideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //实现跳转
                Log.e("=======","======"+position);
                Intent intent = new Intent(SpecialLeftDetalActivity.this, SecondActivity.class);
                String gId = bean.getList().get(position).getAppid();
                intent.putExtra("id",gId);
                intent.putExtra("type",2);
                intent.putExtra("name",bean.getList().get(position).getAppname());
                startActivity(intent);
            }
        });
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bean == null ? 0 : bean.getList().size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView = LayoutInflater.from(SpecialLeftDetalActivity.this)
                        .inflate(R.layout.special_left_second_item,null);
                holder = new ViewHolder(convertView);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(SpecialLeftDetalActivity.this).load(MyApi.BASE_URL+bean.getList().get(position).getAppicon())
                    .placeholder(R.mipmap.def_loading).into(holder.icon);
            holder.name.setText(bean.getList().get(position).getAppname());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SpecialLeftDetalActivity.this, "暂无下载", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            View view;
            @BindView(R.id.special_left_second_item_icon)
            ImageView icon;
            @BindView(R.id.special_left_second_item_name)
            TextView name;
            @BindView(R.id.special_left_second_item_btn)
            Button btn;

            public ViewHolder(View view) {
                this.view = view;
                ButterKnife.bind(this, view);
                view.setTag(this);
            }
        }
    }

}
