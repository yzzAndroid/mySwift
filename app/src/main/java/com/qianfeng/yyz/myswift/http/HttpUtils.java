package com.qianfeng.yyz.myswift.http;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.bean.second.MyGiftBean;
import com.qianfeng.yyz.myswift.callback.BeanCallBack;
import com.qianfeng.yyz.myswift.callback.IGetGiftCallback;
import com.qianfeng.yyz.myswift.json.GetBean;
import com.qianfeng.yyz.myswift.utils.JsonCacheUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 单利模式
 */
public class HttpUtils {

    public static final int GET = 1;
    public static final int POST = 2;
    public static final int SINGIN = 3;
    private static HttpUtils sHttpUtils;
    private  OkHttpClient sOkHttpClient;
    private Handler handler;
    private HttpUtils(){

        sOkHttpClient = new OkHttpClient.Builder().build();
        handler = new Handler();

    }

    public static HttpUtils getInstance(){
        if (null==sHttpUtils){
            sHttpUtils = new HttpUtils();
        }
        return sHttpUtils;
    }

    /**
     * @param path:地址
     * @param t 实体类
     * @param callBack 接口
     * @param <T> 实体类类型
     */
    public  <T> void getBean(String path, final T t, final BeanCallBack callBack, Context context){
        getBean(path,t,callBack,GET,context);
    }
    public  <T> void getBean(String path, final T t, final BeanCallBack callBack, int type, final Context context){


        switch (type) {
            case GET:
                //网络请求
                Request request = new Request.Builder().url(path).build();
                sOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        T t1 = GetBean.getInstance().getBean(json, t);
                        //防护代码
                        if (t1 == null) {
                            t1 = t;
                        }
                        final T finalT = t1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.callback(finalT);
                            }
                        });
                    }
                });
                break;
            case POST:
                FormBody body = new FormBody.Builder()
                        .add("key", path).build();
                Request request1 = new Request.Builder()
                        .post(body)
                        .url(MyApi.Search.SEARCH)
                        .build();
                sOkHttpClient.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();

                        T t1 = GetBean.getInstance().getBean(json, t);
                        if (t1 == null) {
                            t1 = t;
                        }
                        final T finalT = t1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.callback(finalT);
                            }
                        });
                    }
                });
                break;
        }
    }

    public void getGift(String uid, String appid, final Context context, final IGetGiftCallback callback){
        Request request = new Request.Builder().url(String.format(MyApi.GiftApi.GIFT_CODE,uid,appid)).build();
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject object = new JSONObject(json);
                    final String msg = object.getString("returnMsg");
                    final boolean flag = object.getBoolean("flag");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.getGiftCallback(flag,msg);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void taoGift(String uid, String appid, final Context context, final IGetGiftCallback callback){
        Request request = new Request.Builder().url(String.format(MyApi.GiftApi.TAOHAO,uid,appid)).build();
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject object = new JSONObject(json);
                    final String msg = object.getString("returnMsg");
                    final int flag = object.getInt("flag");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.taoGiftCallback(flag,msg);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getMyGift(String path, final BeanCallBack callBack, final Context context){
        Request request = new Request.Builder().url(path).build();
        sOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.callback(json2Bean(json,context));
                    }
                });
            }
        });
    }

    private List<MyGiftBean> json2Bean(String json, final Context context){

        List<MyGiftBean> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int size = array.length();
            for (int i = 0;i<size;i++){
                MyGiftBean bean = new MyGiftBean();
                JSONObject obj = (JSONObject) array.get(i);
                bean.setId(obj.getInt("id"));
                bean.setGiftid(obj.getString("giftid"));
                bean.setCodes(obj.getString("codes"));
                bean.setFlag(obj.getInt("flag"));
                bean.setUid(obj.getString("uid"));
                bean.setUname(obj.getString("uname"));
                bean.setGiftname(obj.getString("giftname"));
                bean.setIconurl(obj.getString("iconurl"));
                bean.setAddtime(obj.getString("addtime"));
                bean.setOvertime(obj.getString("overtime"));
                list.add(bean);
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,context.getString(R.string.sever_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return null;
    }
}
