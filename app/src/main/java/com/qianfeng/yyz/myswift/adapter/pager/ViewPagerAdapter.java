package com.qianfeng.yyz.myswift.adapter.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.qianfeng.yyz.myswift.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ViewPagerAdapter extends PagerAdapter {

    List<View> viewList;
    Context context;
    List<String> path;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
    }

    public ViewPagerAdapter() {
    }

    @Override
    public int getCount() {

        return path.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) viewList.get(position);
        Picasso.with(context).load(path.get(position)).placeholder(R.mipmap.def_loading).into(imageView);
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
