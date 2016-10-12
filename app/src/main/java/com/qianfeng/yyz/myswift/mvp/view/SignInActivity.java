package com.qianfeng.yyz.myswift.mvp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.mvp.presenter.PresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements ISingInView{

    @BindView(R.id.sing_in_phone_numble)
    EditText mPhone;
    @BindView(R.id.sing_in_pwd)
    EditText mPassword;
    @BindView(R.id.sing_in_nuck_name)
    EditText mName;
    @BindView(R.id.sing_in_load)
    LinearLayout mLoad;
    private  PresenterImpl mPresenterImpl;
    public static final String STATE = "state";
    public static final String ID = "id";
    public static final String PSW = "psw";
    public static final String NAME = "name";
    public static final String SDN = "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mPresenterImpl = new PresenterImpl(this,this);
    }

    @Override
    public String getPhoneNumble() {
        return mPhone.getText().toString();
    }

    @Override
    public String getUserName() {
        return mName.getText().toString();
    }

    @Override
    public String getUserPassWord() {
        return mPassword.getText().toString();
    }

    @Override
    public void showErrorPhoneNumble(int type) {
        if (type==PresenterImpl.ONE){
            Toast.makeText(SignInActivity.this,getString(R.string.id_erroy), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SignInActivity.this,getString(R.string.id_erroy), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showErrorUserName(int type) {
        if (type==PresenterImpl.ONE){
            Toast.makeText(SignInActivity.this, getString(R.string.name_erroy_one), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SignInActivity.this, getString(R.string.name_erroy_two), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorPassWord(int type) {
        if (type==PresenterImpl.ONE){
            Toast.makeText(SignInActivity.this,getString(R.string.pwd_erroy), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SignInActivity.this,getString(R.string.pwd_erroy), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void success(String uid) {
        SharedPreferences sharedPreference = getSharedPreferences(SDN, Context.MODE_PRIVATE);
        sharedPreference.edit()
                .putString(NAME,mName.getText().toString())
                .putString(PSW,mPassword.getText().toString())
                .putString(ID,mPhone.getText().toString())
                .putBoolean(STATE,true)
                .putString("uid",uid)
                .commit();
        mLoad.setVisibility(View.GONE);
        Toast.makeText(SignInActivity.this,getString(R.string.sing_success), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failed(String msg) {
        mLoad.setVisibility(View.GONE);
        Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeProgressbar() {
        mLoad.setVisibility(View.GONE);
    }

    //注册按钮
    public void singin(View view) {
        mLoad.setVisibility(View.VISIBLE);
        mPresenterImpl.singIn();
    }


    public void back(View view) {
        finish();
    }
}
