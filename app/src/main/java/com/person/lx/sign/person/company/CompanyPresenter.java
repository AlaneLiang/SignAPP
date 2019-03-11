package com.person.lx.sign.person.company;

import com.person.lx.sign.bean.CompanyBean;

public class CompanyPresenter implements CompanyContract.presenter {
    private CompanyContract.view mView;
    private CompanyModel companyModel;
    CompanyPresenter(CompanyContract.view view){
        this.mView = view;
        companyModel = new CompanyModel();
    }

    @Override
    public void initData(String token, String companyId) {
        companyModel.initDataModel(token, companyId, new CompanyContract.model.getCallBack() {
            @Override
            public void success(CompanyBean companyBean) {
                mView.initData(companyBean);
            }

            @Override
            public void fail(String msg) {

            }
        });
    }
}
