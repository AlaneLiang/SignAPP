package com.person.lx.sign.login;

import com.person.lx.sign.BasePresenter;
import com.person.lx.sign.BaseView;

public interface LoginContract {

    interface View extends BaseView<Present> {
        void loginError(String msg);
        void loginSuccess(String token,String companyId,String phone);
        String getPhone();
        String getPassword();
    }

    interface Present extends BasePresenter {
        void login(String phone,String password);

    }

   interface LoginModel{
         void containLoginResponseData(String phone,String password,CallBack callBack);


         interface CallBack{
            void loginSuccess(String token,String companyId,String phone);
            void loginFailed(String result);
        }
    }


}
