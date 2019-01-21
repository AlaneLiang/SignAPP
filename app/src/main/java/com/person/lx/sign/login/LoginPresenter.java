package com.person.lx.sign.login;

public class LoginPresenter implements LoginContract.Present {
    private final LoginContract.View mView;

    public LoginPresenter(LoginContract.View view)
    {
        this.mView = view;

        //我这里直接把activity作为view，所以不需要
        //mView.setPresenter(this);
    }


    @Override
    public void login() {

    }

    @Override
    public void start() {

    }
}
