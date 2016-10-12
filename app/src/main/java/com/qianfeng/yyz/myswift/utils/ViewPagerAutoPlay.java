package com.qianfeng.yyz.myswift.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.qianfeng.yyz.myswift.R;


import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class ViewPagerAutoPlay {

    private  static Handler handler ;
    private static ViewPager viewPager;
    private static int size ;
    private static Fragment fragment;


    private static void makeVpMove(final ViewGroup point, final ViewPager viewPager, final List<String> path){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               for (int i =0;i<size-1;i++){
                   ImageView imageView = (ImageView) point.getChildAt(i);
                   if (i==position){
                       imageView.setImageResource(R.drawable.view_play);
                   }else {
                       imageView.setImageResource(R.drawable.view_unplay);
                   }
               }
                if (position==size-1){
                    viewPager.setCurrentItem(0,false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public static void initPoint(ViewGroup point, final ViewPager viewPager, List<String> path, final Context context, final Fragment fragment){
        ViewPagerAutoPlay.viewPager = viewPager;
        ViewPagerAutoPlay.fragment = fragment;
        ViewPagerAutoPlay.size = path.size();
        Log.e("===","==="+size);
        for (int i = 0;i<size-1;i++){
            ImageView imageView = new ImageView(context);
            imageView.setPadding(10,0,10,0);
            point.addView(imageView);
            if (i==0){
                imageView.setImageResource(R.drawable.view_play);
            }else {
                imageView.setImageResource(R.drawable.view_unplay);
            }
        }
        //循环
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (null!=msg){
                    if (msg.what==1){
                        if (!fragment.isHidden()){
                            handler.removeMessages(1);
                            autoSwitch();
                        }
                        sendEmptyMessageDelayed(1,3000);
                    }
                }
            }
        };
        //触摸事件
        onTouch();
        makeVpMove(point,viewPager,path);
    }


    private static void autoSwitch(){
        int now = viewPager.getCurrentItem();
        if (now==size-2){
            viewPager.setCurrentItem(0,false);
        }else {
            viewPager.setCurrentItem(now+1,false);
        }
    }

    private static void onTouch(){
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction()==MotionEvent.ACTION_MOVE||event.getAction()==MotionEvent.ACTION_DOWN){
                    handler.removeMessages(1);
                    Log.e("============","========down=====");
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    Log.e("============","========up=====");
                    handler.sendEmptyMessageDelayed(1,3000);
                }
                return false;
            }
        });
    }

    public static void startPlay(){
        if (handler==null){
            return;
        }
        handler.sendEmptyMessageDelayed(1,3000);
    }

    public static void pause(){
        if (handler==null){
            return;
        }
        handler.removeMessages(1);

    }


}
