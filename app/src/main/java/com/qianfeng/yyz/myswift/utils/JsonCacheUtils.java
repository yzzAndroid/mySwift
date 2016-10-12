package com.qianfeng.yyz.myswift.utils;

import android.os.Environment;

import android.util.Log;
import android.util.LruCache;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class JsonCacheUtils {

    private LruCache<String,String> cache;

    private static JsonCacheUtils jsonCacheUtils;

    private JsonCacheUtils(){
        int size = (int) (Runtime.getRuntime().maxMemory()/16);
        cache = new LruCache<String, String>(size){
            @Override
            protected int sizeOf(String key, String value) {
                return value.getBytes().length;
            }
        };
    }

    public static JsonCacheUtils getInstance(){
        if (null==jsonCacheUtils){
            jsonCacheUtils = new JsonCacheUtils();
        }
        return jsonCacheUtils;
    }

    private File getFile(String path){
        File root = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            root = Environment.getExternalStorageDirectory();
        }

        File file = new File(root,"MySwift");
        if (!file.exists()){
            file.mkdirs();
        }
        File file1 = new File(file,path.replace("/","Y"));
        if (!file1.exists()){
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file1;
    }

    public String getNativeJson(String path){
        String json = null;
        json = cache.get(path.replace("/","Y"));
        if (null!=json){
            Log.e("===============","======1111111111=============");
            return json;
        }
        json = getText(getFile(path));
        if (null!=json){
            save(path,json);
            return json;
        }
        return json;
    }

    private String getText(File file){

        StringBuilder sb = new StringBuilder();
        String temp = null;
        BufferedReader reader = null;
        try {
            if (!file.exists()){
                return null;
            }
             reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (null!=(temp = reader.readLine())){
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null!=reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public void save(String path,String json){
        cache.put("path",json);
        saveDisc("path",json);
    }

    public void saveDisc(String path,String json){
        BufferedOutputStream out = null;
        try {
             out = new BufferedOutputStream(new FileOutputStream(getFile(path)));
            out.write(json.getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
