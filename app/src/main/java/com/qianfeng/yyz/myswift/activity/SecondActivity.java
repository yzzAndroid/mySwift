package com.qianfeng.yyz.myswift.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.GiftSecondBean;
import com.qianfeng.yyz.myswift.fragment.second.SecondGiftFragment;
import com.qianfeng.yyz.myswift.fragment.second.SecondOpenFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.second_share)
    ImageButton mShare;
    @BindView(R.id.second_title_gname)
    TextView mName;
    private int type;
    private String mId;
    private String gName;


    public static final int TYPE_GIFT = 1;
    public static final int TYPE_OPEN_FU = 2;
    public static final int TYPE_OPEN_CE = 3;
    public static final int TYPE_OPEN_Hot = 4;
    public SecondGiftFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        gName = intent.getStringExtra("name");
        mId = intent.getStringExtra("id");
        type = intent.getIntExtra("type",1);
        initContral();
    }

    private void initContral() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (type){
            case TYPE_GIFT:
                //设置name
                mName.setText(gName);
                mShare.setVisibility(View.VISIBLE);
                fragment = new SecondGiftFragment();
                Bundle bundle = new Bundle();
                String path = String.format(MyApi.GiftApi.GIFT_DETAL,mId);
                bundle.putString("path",path);
                fragment.setArguments(bundle);
                transaction.replace(R.id.second_container,fragment);
                break;
            case TYPE_OPEN_FU:
                mName.setText(gName);
                mShare.setVisibility(View.GONE);
                Fragment secondOpenFragment = new SecondOpenFragment();
                String pathOpen = String.format(MyApi.Open.OPEN_DETAL,mId);
                Bundle bundleOpen = new Bundle();
                bundleOpen.putString("path",pathOpen);
                secondOpenFragment.setArguments(bundleOpen);
                transaction.replace(R.id.second_container,secondOpenFragment);
                break;
            case TYPE_OPEN_CE:
                mName.setText(gName);
                mShare.setVisibility(View.GONE);
                mShare.setVisibility(View.GONE);
                Fragment secondOpenFragment2 = new SecondOpenFragment();
                String pathOpen2 = String.format(MyApi.Open.OPEN_DETAL,mId);
                Bundle bundleOpen2 = new Bundle();
                bundleOpen2.putString("path",pathOpen2);
                secondOpenFragment2.setArguments(bundleOpen2);
                transaction.replace(R.id.second_container,secondOpenFragment2);

                break;
            case TYPE_OPEN_Hot:
                mName.setText(gName);
                mShare.setVisibility(View.GONE);
                mShare.setVisibility(View.GONE);
                Fragment secondOpenFragment3 = new SecondOpenFragment();
                String pathOpen3 = String.format(MyApi.Open.OPEN_DETAL,mId);
                Bundle bundleOpen3 = new Bundle();
                bundleOpen3.putString("path",pathOpen3);
                secondOpenFragment3.setArguments(bundleOpen3);
                transaction.replace(R.id.second_container,secondOpenFragment3);
                break;
        }
        transaction.commit();
    }


    public void back(View view) {
        finish();
    }

    public void share(View view) {

       GiftSecondBean bean =  fragment.mGiftSecondBean;
        if (bean!=null){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT,bean.getInfo().getGname());
            //intent.putExtra(Intent.EXTRA_SUBJECT,"share");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,"分享到"));
        }
    }
}
