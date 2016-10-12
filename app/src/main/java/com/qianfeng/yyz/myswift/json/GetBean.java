package com.qianfeng.yyz.myswift.json;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class GetBean {

    private static GetBean sGetBean;
    private Gson gson;

    private GetBean() {

        gson = new Gson();
    }

    public static GetBean getInstance(){
        if (null==sGetBean){
            sGetBean = new GetBean();
        }
        return sGetBean;
    }

    public  <T> T getBean(String json, T t){

            if (json.equals("{\"list\":}")){
                return null;
            }


        T t1 = (T) gson.fromJson(json,t.getClass());
        return t1;
    }
}
