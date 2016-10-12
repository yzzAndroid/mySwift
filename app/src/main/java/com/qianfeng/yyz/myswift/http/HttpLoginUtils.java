package com.qianfeng.yyz.myswift.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.api.MyApi;
import com.qianfeng.yyz.myswift.callback.ILoginCallback;
import com.qianfeng.yyz.myswift.callback.ISendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class HttpLoginUtils {
    public static final String UNAME = "uname";
    public static final String PWD = "pwd";
    public static final String NICKNAME = "nickname";
    public static final String ERRORY = "服务器维护中";
    public static final String NAME = "name";
    public static final String CONTACt = "contact";
    public static final String DESC = "desc";

    private static Handler handler = new Handler();

    public static void singIn(String phone, String pwd, String name, final ILoginCallback callback, final Context context){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(UNAME,phone)
                .add(PWD,pwd)
                .add(NICKNAME,name)
                .build();
        Request request = new Request.Builder()
                .url(MyApi.Login.SINGIN)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context,context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject object = new JSONObject(json);
                    final String msg = object.getString("returnMsg");
                    final boolean flag = object.getBoolean("flag");
                    if (flag){

                        final String uid = object.getString("uid");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(flag,msg,uid);
                            }
                        });
                    }else {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(flag,msg,null);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void login(String phone, String pwd, final ILoginCallback callback, final Context context){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(NAME ,phone)
                .add(PWD,pwd)
                .build();
        Request request = new Request.Builder()
                .url(MyApi.Login.LOGIN)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
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
                String json = response.body().string();
                Log.e("=","=json"+json);
                try {
                    JSONObject object = new JSONObject(json);
                    final String msg = object.getString("returnMsg");
                    final boolean flag = object.getBoolean("flag");
                    if (flag){
                        JSONObject object1 = object.getJSONObject("info");
                        final String unickname = object1.getString("nickname");
                        final String path = object1.getString("headimg");
                        final String uid = object1.getString("uid");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback1(flag,msg,unickname,path,uid);
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback1(flag,msg,null,null,null);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback1(false,ERRORY,null,null,null);
                        }
                    });
                }
            }
        });
    }

    public static void sendEmail(String contact, String desc, final ISendCallback callback, final Context context){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(CONTACt ,contact)
                .add(PWD,desc)
                .build();
        Request request = new Request.Builder()
                .url(MyApi.Send.SEND)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
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
                String json = response.body().string();
                try {
                    JSONObject object = new JSONObject(json);
                    boolean flag = object.getBoolean("flag");
                    String returnMsg = object.getString("returnMsg");
                    callback.callback(flag,returnMsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
