package com.qianfeng.yyz.myswift.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.mvp.presenter.PresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements ILoginView{

    @BindView(R.id.login_id)
    EditText mId;
    @BindView(R.id.login_pwd)
    EditText mPwd;
    @BindView(R.id.login_in_load)
    LinearLayout mLoade;
    PresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        presenter = new PresenterImpl(this);
    }

    @Override
    protected void onResume() {
        initState();
        super.onResume();
    }

    private void initState() {
        boolean state = getSharedPreferences(SignInActivity.SDN, Context.MODE_PRIVATE).getBoolean(SignInActivity.STATE,false);
        if (state){
            finish();
        }
    }

    public void ringin(View view) {
        startActivity(new Intent(this,SignInActivity.class));
    }

    public void back(View view) {
        finish();
    }

    @Override
    public String getID() {
        return mId.getText().toString();
    }

    @Override
    public String getPwd() {
        return mPwd.getText().toString();
    }

    @Override
    public void showErroyId(int type) {
        if (type==PresenterImpl.ONE){
            Toast.makeText(LogInActivity.this,getString(R.string.id_erroy), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LogInActivity.this,getString(R.string.id_erroy), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErroyPwd(int type) {
        if (type==PresenterImpl.ONE){
            Toast.makeText(LogInActivity.this,getString(R.string.pwd_erroy), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LogInActivity.this,getString(R.string.pwd_erroy), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void success(String msg,String unikname,String path,String uid) {
        SharedPreferences sharedPreferences = getSharedPreferences(SignInActivity.SDN,Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean(SignInActivity.STATE,true)
                .putString(SignInActivity.ID,mId.getText().toString())
                .putString(SignInActivity.NAME,unikname)
                .putString(SignInActivity.PSW,mPwd.getText().toString())
                .putString("uid",uid)
                .commit();
        finish();
        Toast.makeText(LogInActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        mLoade.setVisibility(View.GONE);
    }

    @Override
    public void failed(String msg) {
        Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
        mLoade.setVisibility(View.GONE);
    }

    @Override
    public void closeProgress() {
        mLoade.setVisibility(View.GONE);
    }

    public void login(View view) {
        mLoade.setVisibility(View.VISIBLE);
        presenter.login();
    }
}
