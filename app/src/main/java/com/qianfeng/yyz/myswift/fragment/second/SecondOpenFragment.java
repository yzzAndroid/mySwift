package com.qianfeng.yyz.myswift.fragment.second;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.OpenSecondBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondOpenFragment extends Fragment implements BeanCallBack{
    @BindView(R.id.open_second_img)
    ImageView mImageView;
    @BindView(R.id.open_second_type)
    TextView mType;
    @BindView(R.id.open_second_size)
    TextView mSize;
    @BindView(R.id.open_second_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.open_second_game_description)
    TextView mGameDescription;
    @BindView(R.id.open_second_name)
    TextView mName;
    private OpenSecondBean mOpenSecondBean;
    @BindView(R.id.open_second_loading)
    LinearLayout mLoading;
    @BindView(R.id.open_second_download)
    Button mCheck;

    public SecondOpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_open, container, false);
        ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        String path = bundle.getString("path");
        Log.e("path","="+path);
        HttpUtils.getInstance().getBean(path,new OpenSecondBean(),this,getActivity());
        return view;
    }

    private void initView() {
        Picasso.with(getActivity()).load(MyApi.BASE_URL+mOpenSecondBean.getApp().getLogo())
        .placeholder(R.mipmap.def_loading).into(mImageView);
        mName.setText(mOpenSecondBean.getApp().getName());
        mType.setText(mOpenSecondBean.getApp().getTypename());
        String ssize = mOpenSecondBean.getApp().getAppsize();
        if (TextUtils.isEmpty(ssize)){
            mSize.setText(getString(R.string.un_known));
        }else {
            mSize.setText(ssize);
        }

        //添加horizontalScrollVView
        int size = mOpenSecondBean.getImg().size();
        for (int i = 0;i<size;i++){
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.image_view,null);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.open_second_himg);
            Picasso.with(getActivity())
                    .load(MyApi.BASE_URL+mOpenSecondBean.getImg().get(i).getAddress())
                    .placeholder(R.mipmap.def_loading)
                    .into(imageView);
            mLinearLayout.addView(linearLayout);
        }
        mGameDescription.setText(mOpenSecondBean.getApp().getDescription());
        String uri = mOpenSecondBean.getApp().getDownload_addr();
        if (TextUtils.isEmpty(uri)){
            mCheck.setBackgroundResource(R.color.special_bottom);
            mCheck.setText(getString(R.string.un_download));
        }
    }

    @Override
    public <T> void callback(T t) {
        mOpenSecondBean = (OpenSecondBean) t;
        mLoading.setVisibility(View.GONE);
        initView();
    }

    @OnClick(R.id.open_second_download)
    public void onclick(){
        if (mOpenSecondBean==null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String uri = mOpenSecondBean.getApp().getDownload_addr();
        if (TextUtils.isEmpty(uri)){
            return;
        }
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}
