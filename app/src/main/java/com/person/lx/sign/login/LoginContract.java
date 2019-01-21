package com.person.lx.sign.login;

import com.person.lx.sign.BasePresenter;
import com.person.lx.sign.BaseView;

public interface LoginContract {

    interface View extends BaseView<Present> {
        void loginError(String msg);
        void loginSuccess();
        String getAccount();
        String getPassword();
    }

    interface Present extends BasePresenter {
        void login();
    }


}
