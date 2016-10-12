package com.qianfeng.yyz.myswift.mvp.modal;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface ISingInModal {

    void singin(String phone, String psw, String name, ISingInCallback callback, Context context);
}
