package com.person.lx.sign.person.info;

import com.person.lx.sign.bean.PersonDeatilBean;

public class InfoPresenter implements InfoContract.presenter {
    private InfoContract.view mView;
    private InfoModel infoModel;
    InfoPresenter(InfoContract.view view){
        this.mView = view;
        infoModel = new InfoModel();
    }

    @Override
    public void initData(String token) {
        infoModel.initDataModel(token, new InfoContract.model.getInfoCallBack() {
            @Override
            public void success(PersonDeatilBean personDeatil) {
                mView.initData(personDeatil);
            }

            @Override
            public void fail(String msg) {
               mView.showMessage(msg);
            }
        });
    }
}
