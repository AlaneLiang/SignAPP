package com.person.lx.sign.person;

public class PersonPresenter implements PersonContract.presenter {
    private PersonContract.view mView;
    private PersonModel personModel;
    PersonPresenter(PersonContract.view view){
        this.mView = view;
        personModel = new PersonModel();
        }
    @Override
    public void initData(String token, String companyId) {
       personModel.initDataModel(token, companyId, new PersonContract.model.initCallBack() {
           @Override
           public void success(String img, String name) {
           mView.initData(img,name);
           }

           @Override
           public void fail(String msg) {

           }
       });
    }
}
