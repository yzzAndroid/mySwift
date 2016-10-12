package com.qianfeng.yyz.myswift.callback;

/**
 * Created by Administrator on 2016/9/26 0026.
 *
 * 网络请求的回调接口
 */
public interface BeanCallBack {

    <T> void callback(T t);
}
