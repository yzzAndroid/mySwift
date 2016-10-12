package com.qianfeng.yyz.myswift.mvp.modal;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface ILoginModal {

    void login(String phone, String psw, IMLoginCallback callback, Context context);
}
