package com.qianfeng.yyz.myswift.callback;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface ILoginCallback {

    void callback(boolean flag,String msg,String uid);
    void callback1(boolean flag,String mdg,String unilname,String imgPath,String uid);
}
