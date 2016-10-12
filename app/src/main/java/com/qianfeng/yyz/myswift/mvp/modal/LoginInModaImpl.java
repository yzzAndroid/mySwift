package com.qianfeng.yyz.myswift.mvp.modal;

import android.content.Context;

import com.qianfeng.yyz.myswift.callback.ILoginCallback;
import com.qianfeng.yyz.myswift.http.HttpLoginUtils;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class LoginInModaImpl implements ILoginModal,ILoginCallback{

    private IMLoginCallback callback;
    @Override
    public void login(String phone, String psw, IMLoginCallback callback, Context context) {
        this.callback = callback;
        HttpLoginUtils.login(phone,psw,this,context);
    }

    @Override
    public void callback(boolean flag, String msg,String uid) {

    }

    @Override
    public void callback1(boolean flag, String msg, String unilname, String imgPath,String uid) {
        if (flag){
            callback.success(msg,unilname,imgPath,uid);
        }else {
            callback.failed(msg);
        }
    }
}
