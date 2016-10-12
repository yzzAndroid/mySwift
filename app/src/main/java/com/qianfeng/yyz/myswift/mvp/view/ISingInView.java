package com.qianfeng.yyz.myswift.mvp.view;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface ISingInView {

    String getPhoneNumble();
    String getUserName();
    String getUserPassWord();
    void showErrorPhoneNumble(int type);
    void showErrorUserName(int type);
    void showErrorPassWord(int type);
    void success(String uid);
    void failed(String msg);
    void closeProgressbar();
}
