package com.qianfeng.yyz.myswift.mvp.view;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface ILoginView {

    String getID();
    String getPwd();
    void showErroyId(int type);
    void showErroyPwd(int type);
    void success(String msg,String unikname,String path,String uid);
    void failed(String msg);
    void closeProgress();
}
