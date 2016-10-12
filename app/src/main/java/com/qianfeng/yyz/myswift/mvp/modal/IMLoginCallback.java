package com.qianfeng.yyz.myswift.mvp.modal;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface IMLoginCallback {

    void success(String msg,String unikname,String path,String uid);
    void failed(String msg);
}
