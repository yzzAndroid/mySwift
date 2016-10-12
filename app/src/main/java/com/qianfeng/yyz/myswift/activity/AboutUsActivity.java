package com.qianfeng.yyz.myswift.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity {

    private String mVersionName;
    private ListView mLisView;
    private List<String> mList;
    private List<String> mList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            mVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mList1 = new ArrayList<>();
        mList.add(getString(R.string.weixin));
        mList.add(getString(R.string.phone));
        mList.add(getString(R.string.pc_local));
        mList.add(getString(R.string.email));
        mList.add(getString(R.string.player_people));
        mList.add(getString(R.string.versionName));
        mLisView = (ListView) findViewById(R.id.about_list);
        mList1.add(getString(R.string.weixin_1));
        mList1.add(getString(R.string.phone_1));
        mList1.add(getString(R.string.pc_local_1));
        mList1.add(getString(R.string.email_1));
        mList1.add(getString(R.string.player_people_1));
        mList1.add(mVersionName);
        MyAdapter adapter = new MyAdapter();
        mLisView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(AboutUsActivity.this).inflate(R.layout.about_item,null);
            TextView tv1 = (TextView) convertView.findViewById(R.id.about_tv_text);
            TextView tv2 = (TextView) convertView.findViewById(R.id.about_tv);
            tv1.setText(mList.get(position));
            tv2.setText(mList1.get(position));
            return convertView;
        }
    }
}
