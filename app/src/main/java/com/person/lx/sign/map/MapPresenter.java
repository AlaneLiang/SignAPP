package com.person.lx.sign.map;

import com.person.lx.sign.bean.CompanyBean;

public class MapPresenter implements MapContract.Present {
    private final MapContract.View mView;
    private MapModel mapModel;
    public MapPresenter(MapContract.View view){
        this.mView = view;
        mapModel = new MapModel();

   }
    @Override
    public void getCompanyInfo(String companyId) {
        mapModel.getCompanyInfoModel(companyId, new MapContract.Model.CallBack() {
            @Override
            public void success(CompanyBean info) {

            }

            @Override
            public void failed(String msg) {

            }
        });
    }
}
