package com.qianfeng.yyz.myswift.fragment.second;


import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.activity.MyGiftActivity;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.GiftSecondBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.callback.IGetGiftCallback;
import com.qianfeng.yyz.myswift.http.HttpUtils;
import com.qianfeng.yyz.myswift.mvp.view.LogInActivity;
import com.qianfeng.yyz.myswift.mvp.view.SignInActivity;
import com.qianfeng.yyz.myswift.utils.BitMapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondGiftFragment extends Fragment implements BeanCallBack,IGetGiftCallback{

    private String mPath;
    public GiftSecondBean mGiftSecondBean;
    @BindView(R.id.gift_second_img)
    ImageView mImageViewBackground;
    @BindView(R.id.second_cirimg)
    CircleImageView mCircleImageView;
    @BindView(R.id.second_validity)
    TextView mValidity;
    @BindView(R.id.second_num)
    TextView mNum;
    @BindView(R.id.second_gift_info)
    TextView mInfo;
    @BindView(R.id.second_get_way)
    TextView mWay;
    @BindView(R.id.second_gift_btn)
    Button mBtn;
    public SecondGiftFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_gift, container, false);
        ButterKnife.bind(this,view);
        mPath = getArguments().getString("path");
        HttpUtils.getInstance().getBean(mPath,new GiftSecondBean(),this,getActivity());
        return view;
    }

    @Override
    public <T> void callback(T t) {
        mGiftSecondBean = (GiftSecondBean) t;
        initView();
    }

    private void initView() {
        final GiftSecondBean.InfoBean info = mGiftSecondBean.getInfo();
        Picasso.with(getActivity()).load(MyApi.BASE_URL+info.getIconurl()).placeholder(R.mipmap.def_loading)
//                .placeholder(R.mipmap.def_loading).transform(new Transformation() {
//            @Override
//            public Bitmap transform(Bitmap source) {
//                Bitmap bitmap = BitMapUtils.fastblur(source,10);
//                if (bitmap!=source){
//                    source.recycle();
//                }
//                return bitmap;
//            }
//
//            @Override
//            public String key() {
//                return info.getIconurl();
//            }
//        })
                .into(mImageViewBackground);
        Picasso.with(getActivity()).load(MyApi.BASE_URL+info.getIconurl()).placeholder(R.mipmap.def_loading).into(mCircleImageView);
        mValidity.setText(info.getOvertime());
        mNum.setText(String.valueOf(info.getExchanges()));
        mInfo.setText(info.getExplains());
        mWay.setText(info.getDescs());
        if (info.getExchanges()<=0){

            mBtn.setText(getResources().getString(R.string.gift_button_less_text));
        }else {

            mBtn.setText(getResources().getString(R.string.gift_button_text));
        }
    }


    @OnClick(R.id.second_gift_btn)
    public void clickGift(){
        if (mGiftSecondBean==null){
            return;
        }
        boolean state = getActivity().getSharedPreferences(SignInActivity.SDN,Context.MODE_PRIVATE).getBoolean(SignInActivity.STATE,false);
        if (!state){
            startActivity(new Intent(getActivity(), LogInActivity.class));
        }
        String uid = getActivity().getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE).getString("uid","1");
        String id = mGiftSecondBean.getInfo().getId();

        if (mGiftSecondBean.getInfo().getExchanges()<=0){
            //淘号
            HttpUtils.getInstance().taoGift(uid,id,getActivity(),this);
        }else{
            //领取code
            HttpUtils.getInstance().getGift(uid,id,getActivity(),this);
        }
    }
    @OnClick(R.id.second_gift_img)
    public void checkGift(){
        boolean state = getActivity().getSharedPreferences(SignInActivity.SDN,Context.MODE_PRIVATE).getBoolean(SignInActivity.STATE,false);
        if (state){

            startActivity(new Intent(getActivity(), MyGiftActivity.class));
        }else {
            startActivity(new Intent(getActivity(), LogInActivity.class));
        }

    }

    @Override
    public void getGiftCallback(boolean flag, String msg) {
        if (flag){
            creatDialog(msg);
        }else {
            creatFDialog(msg);
        }
    }

    //淘号
    @Override
    public void taoGiftCallback(int flag, String msg) {
        creatFDialog(msg);
    }

    private void creatDialog(final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.gift_dialog_item,null);
        TextView tv = (TextView) view.findViewById(R.id.dialog_tv);
        Button copy = (Button) view.findViewById(R.id.copy);
        Button cancle = (Button) view.findViewById(R.id.cancle);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().getDecorView().setPadding(30,0,30,0);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white);
        WindowManager.LayoutParams lp =  dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        //配置
        tv.setText(msg);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText(msg);
                Toast.makeText(getActivity(),getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void creatFDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_gift_failed,null);
        TextView tv = (TextView) view.findViewById(R.id.dialog_tv_f);
        Button close = (Button) view.findViewById(R.id.close);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().getDecorView().setPadding(30,0,30,0);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rectangle_white);
        WindowManager.LayoutParams lp =  dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        //配置
        tv.setText(msg);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
