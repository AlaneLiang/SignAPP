package com.person.lx.sign.map;

import android.os.Handler;

import com.person.lx.sign.bean.CompanyBean;

public class MapPresenter implements MapContract.Present {
     final MapContract.View mView ;
    private MapModel mapModel;
    private Handler mHandler = new Handler();
    public MapPresenter(MapContract.View view){
        this.mView = view;
        mapModel = new MapModel();

   }
    @Override
    public void getCompanyInfo(String token,String companyId) {
        mapModel.getCompanyInfoModel(token,companyId, new MapContract.Model.CallBack() {
            @Override
            public void success(CompanyBean info) {
              mView.covered(info);
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    @Override
    public void sign(String token, String phone, String companyId) {
        mapModel.signModel(token,phone,companyId, new MapContract.Model.signCallBack(){

            @Override
            public void success(String msg) {
              if (msg.equals("签到成功")){
                  mView.showSucceedDialog();
              }else {
                  mView.showSucceedOutDialog();
              }
            }

            @Override
            public void failed(String msg) {
             mView.showMsg(msg);
            }
        });
    }
}
