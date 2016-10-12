package com.qianfeng.yyz.myswift.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.qianfeng.yyz.myswift.mvp.modal.ILoginModal;
import com.qianfeng.yyz.myswift.mvp.modal.IMLoginCallback;
import com.qianfeng.yyz.myswift.mvp.modal.ISingInCallback;
import com.qianfeng.yyz.myswift.mvp.modal.ISingInModal;
import com.qianfeng.yyz.myswift.mvp.modal.LoginInModaImpl;
import com.qianfeng.yyz.myswift.mvp.modal.SingInModaImpl;
import com.qianfeng.yyz.myswift.mvp.view.ILoginView;
import com.qianfeng.yyz.myswift.mvp.view.ISingInView;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PresenterImpl implements IPresenter {

    private ISingInModal mISingInModal;
    private ISingInView mISingView;
    private ILoginView mIloginView;
    private ILoginModal mLoginModal;
    public static final int ONE = 1;
    public static final int TWO = 2;
    private String phone;
    private String name;
    private String pwd;
    private Context context;

    public PresenterImpl(ISingInView mISingView,Context context) {
        this.mISingView = mISingView;
        mISingInModal = new SingInModaImpl();
        this.context = context;
    }

    public PresenterImpl(ILoginView mIloginView){
        this.mIloginView = mIloginView;
        mLoginModal = new LoginInModaImpl();
    }

    @Override
    public void singIn() {
       if (checkInput()){
           mISingInModal.singin(phone, pwd, name, new ISingInCallback() {
               @Override
               public void success(String uid) {
                   mISingView.success(uid);
               }

               @Override
               public void failed(String msg) {
                    mISingView.failed(msg);
               }
           },context);
       }
    }

    @Override
    public void login() {
        if (checkInputLogin()){
            mLoginModal.login(phone, pwd, new IMLoginCallback() {
                @Override
                public void success(String msg,String unikname,String path,String uid) {
                    mIloginView.success(msg,unikname,path,uid);
                }

                @Override
                public void failed(String msg) {
                    mIloginView.failed(msg);
                }
            },context);
        }
    }

    private boolean checkInputLogin() {
        phone = mIloginView.getID();
        pwd = mIloginView.getPwd();
        if (TextUtils.isEmpty(phone)){
            mIloginView.showErroyId(ONE);
            mIloginView.closeProgress();
            return false;
        }
        if (phone.length()!=11){
            mIloginView.showErroyId(TWO);
            mIloginView.closeProgress();
            return false;
        }
        if (TextUtils.isEmpty(pwd)){
            mIloginView.showErroyPwd(ONE);
            mIloginView.closeProgress();
            return false;
        }
        if (pwd.length()<6||pwd.length()>16){
            mIloginView.showErroyPwd(TWO);
            mIloginView.closeProgress();
            return false;
        }
        return true;
    }

    private boolean checkInput(){
       phone  = mISingView.getPhoneNumble();
       name  = mISingView.getUserName();
       pwd = mISingView.getUserPassWord();
        if (TextUtils.isEmpty(phone)){
            mISingView.showErrorPhoneNumble(ONE);
            mISingView.closeProgressbar();
            return false;
        }
        if (phone.length()!=11){
            mISingView.showErrorPhoneNumble(TWO);
            mISingView.closeProgressbar();
            return false;
        }
        if (TextUtils.isEmpty(pwd)){
            mISingView.showErrorPassWord(ONE);
            mISingView.closeProgressbar();
            return false;
        }
        if (pwd.length()<6||pwd.length()>16){
            mISingView.showErrorPassWord(TWO);
            mISingView.closeProgressbar();
            return false;
        }
        if (TextUtils.isEmpty(name)){
            mISingView.showErrorUserName(ONE);
            mISingView.closeProgressbar();
            return false;
        }
        if (name.length()>8){
            mISingView.showErrorUserName(TWO);
            mISingView.closeProgressbar();
            return false;
        }
        return true;
    }
}
