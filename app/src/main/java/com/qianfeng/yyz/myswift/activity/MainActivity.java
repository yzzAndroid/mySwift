package com.qianfeng.yyz.myswift.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.bean.OpenFuBean;
import com.qianfeng.yyz.myswift.fragment.GiftFragment;
import com.qianfeng.yyz.myswift.fragment.HotFragment;
import com.qianfeng.yyz.myswift.fragment.OpenFragment;
import com.qianfeng.yyz.myswift.fragment.SpecialFragment;
import com.qianfeng.yyz.myswift.mvp.view.LogInActivity;
import com.qianfeng.yyz.myswift.mvp.view.SignInActivity;
import com.qianfeng.yyz.myswift.view.MyRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Fragment mCurrentFragemt;
    private String mUserName;
    public static final String IMGURI = "img";
    public static final String TEXT = "text";

    @BindView(R.id.bottom_gb)
    RadioGroup mRadioGroup;
    @BindView(R.id.head_tab_name_tv)
    TextView mTitle;
    @BindView(R.id.home_spl)
    SlidingPaneLayout mSlidingPaneLayout;
    @BindView(R.id.content_right)
    MyRelativeLayout mRelativeLayout;
    @BindView(R.id.head_gift_search_tv)
    TextView mSearch;
    @BindView(R.id.left_name)
    TextView mName;
    @BindView(R.id.left_img)
    CircleImageView mImg;
    @BindView(R.id.left_listview)
    ListView mListViewLeft;





    private GiftFragment mGiftFragment;
    private OpenFragment mOpenFragment;
    private HotFragment mHotFragment;
    private SpecialFragment mSpecialFragment;
    public static boolean state = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottomView();
        initSpl();
        checkState();
        initViews();

    }

    private void initViews() {
        mRelativeLayout.setSlidingPaneLayout(mSlidingPaneLayout);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        Map<String,Object> map3 = new HashMap<>();
        Map<String,Object> map4 = new HashMap<>();
        map1.put(IMGURI,R.mipmap.icon_home);
        map1.put(TEXT,getString(R.string.home));
        map2.put(IMGURI,R.mipmap.my_gift);
        map2.put(TEXT,getString(R.string.my_gift));
        map3.put(IMGURI,R.mipmap.send_email);
        map3.put(TEXT,getString(R.string.send_email));
        map4.put(IMGURI,R.mipmap.about_me);
        map4.put(TEXT,getString(R.string.about_us));
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.left_item,
                new String[]{IMGURI,TEXT},new int[]{R.id.left_item_img,R.id.left_item_text});
        mListViewLeft.setAdapter(adapter);

        mListViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    mSlidingPaneLayout.closePane();
                    break;
                case 1:
                    if (!state){
                        startActivity(new Intent(MainActivity.this,LogInActivity.class));

                    }else {
                        startActivity(new Intent(MainActivity.this,MyGiftActivity.class));
                    }
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this,SendEmailActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                    break;
            }
            }
        });
    }


    private void checkState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE);
        state = sharedPreferences.getBoolean(SignInActivity.STATE,false);
        if (state){
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStaet();
        initUser();
    }

    private void initUser() {
        if (state){
            mName.setText(mUserName);
            mImg.setImageResource(R.mipmap.def_head);
        }
    }

    private void initStaet() {
        SharedPreferences sharedPreferences = getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE);
        state = sharedPreferences.getBoolean(SignInActivity.STATE,false);
        if (state){
            mUserName = sharedPreferences.getString(SignInActivity.NAME,"");
        }
    }

    @OnClick(R.id.content_right)
    public void click(){
        if (mSlidingPaneLayout.isOpen()){
            mSlidingPaneLayout.closePane();
        }
    }

    private void initSpl() {
        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//               mRelativeLayout.setScaleY(1-slideOffset*0.5f);
//                mSlidingPaneLayout.setScaleX(1-slideOffset*0.5f);

               mSlidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
                //mSlidingPaneLayout.setShadowResourceRight(R.mipmap.shadow);
                float scale = 1-slideOffset*0.3f;
                panel.setScaleX(scale);
                panel.setScaleY(scale);
                mRelativeLayout.setEnabled(false);
                mRelativeLayout.setClickable(false);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
    }

    private void initBottomView() {
        mGiftFragment = new GiftFragment();
        mOpenFragment = new OpenFragment();
        mHotFragment = new HotFragment();
        mSpecialFragment = new SpecialFragment();
        //先添加礼包的碎片
        getSupportFragmentManager().beginTransaction().add(R.id.head_container,mGiftFragment).commit();
        //给mCurrentFragemt赋值
        mCurrentFragemt = mGiftFragment;
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bottom_gift:
                        mSearch.setVisibility(View.VISIBLE);
                        switchFragment(mGiftFragment);
                        mTitle.setText(getResources().getText(R.string.gift_title));
                        break;
                    case R.id.bottom_open:
                        mSearch.setVisibility(View.GONE);
                        switchFragment(mOpenFragment);
                        mTitle.setText(getResources().getText(R.string.open_title));
                        break;
                    case R.id.bottom_special:
                        mSearch.setVisibility(View.GONE);
                        switchFragment(mSpecialFragment);
                        mTitle.setText(getResources().getText(R.string.special_title));
                        break;
                    case R.id.bottom_hot:
                        mSearch.setVisibility(View.GONE);
                        switchFragment(mHotFragment);
                        mTitle.setText(getResources().getText(R.string.hot_title));
                        break;
                }
            }
        });
    }

    /**
     * @param fragment 要显示的Fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()){
            transaction.hide(mCurrentFragemt).add(R.id.head_container,fragment);
        }else {
            transaction.hide(mCurrentFragemt).show(fragment);
        }
        mCurrentFragemt = fragment;
        transaction.commit();
    }

    public void contral(View view) {
        if (mSlidingPaneLayout.isOpen()){
            mSlidingPaneLayout.closePane();
        }else {
            mSlidingPaneLayout.openPane();
        }
    }


    public void search(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        boolean state = getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE).getBoolean(SignInActivity.STATE,false);
        if (state){

        }else {
            startActivity(new Intent(this, LogInActivity.class));
        }

    }
}
