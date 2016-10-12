package com.qianfeng.yyz.myswift.mvp.modal;

import android.content.Context;

import com.qianfeng.yyz.myswift.callback.ILoginCallback;
import com.qianfeng.yyz.myswift.http.HttpLoginUtils;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class SingInModaImpl implements ISingInModal,ILoginCallback {

    private ISingInCallback mISingInCallback;
    @Override
    public void singin(String phone, String psw, String name, ISingInCallback callback, Context context) {
        mISingInCallback = callback;
        HttpLoginUtils.singIn(phone,psw,name,this,context);
    }

    @Override
    public void callback(boolean flag, String msg,String uid) {
        if (flag){
            mISingInCallback.success(uid);
        }else {
            mISingInCallback.failed(msg);
        }
    }

    @Override
    public void callback1(boolean flag, String mdg, String unilname, String imgPath,String uid) {

    }
}
