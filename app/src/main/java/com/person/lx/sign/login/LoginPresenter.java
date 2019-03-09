package com.person.lx.sign.login;

import android.os.Handler;

public class LoginPresenter implements LoginContract.Present {
    private final LoginContract.View mView;
    private LoginModelImpl loginModel;
    private Handler mHandler = new Handler();
    public LoginPresenter(LoginContract.View view)
    {
        this.mView = view;
         loginModel = new LoginModelImpl();
        //我这里直接把activity作为view，所以不需要
        //mView.setPresenter(this);
    }


    @Override
    public void login(String phone,String password) {
           loginModel.containLoginResponseData(phone, password, new LoginContract.LoginModel.CallBack() {
               @Override
               public void loginSuccess(final String token,final String companyId,final String phone) {
                   //handler交给主线程更新UI
                   mHandler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                          mView.loginSuccess(token,companyId,phone);
                       }
                   },500);
               }

               @Override
               public void loginFailed(final String result) {
                   mHandler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                          mView.loginError(result);

                       }
                   },500);
               }
           });
    }

    @Override
    public void start() {

    }
}
