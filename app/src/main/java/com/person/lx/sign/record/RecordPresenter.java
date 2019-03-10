package com.person.lx.sign.record;

import android.os.Handler;

import com.person.lx.sign.bean.SignLogBean;
import com.person.lx.sign.customView.DayManager;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class RecordPresenter implements RecordContract.presenter {
    final RecordContract.view mView ;
    private RecordModel recordModel;
    private Handler mHandler = new Handler();
    public  RecordPresenter(RecordContract.view view){
        this.mView = view;
        recordModel = new RecordModel();

    }
    @Override
    public void initData(String token, String phone, String companyId, String year, String month) {
        recordModel.initDataModal(token, phone, companyId, year, month, new RecordContract.model.initCallBack() {
            @Override
            public void success(List<SignLogBean> list) {
             for (SignLogBean signLogBean : list){
                 if (signLogBean.getIsactive() == 0){
                     int date = Integer.parseInt(signLogBean.getSignIn().substring(8,10));
                     DayManager.removeOutDays(date);
                     DayManager.addOutDays(date);
                 }

                 if (StringUtils.isNotBlank(signLogBean.getSignIn())
                         && StringUtils.isNotBlank(signLogBean.getSignOut())
                         && signLogBean.getIsactive() != 0 ){
                     int date = Integer.parseInt(signLogBean.getSignIn().substring(8,10));
                     DayManager.addNomalDays(date);
                 }

                 if (StringUtils.isNotBlank(signLogBean.getSignIn())
                         && !StringUtils.isNotBlank(signLogBean.getSignOut())
                         && signLogBean.getIsactive() != 0 ){
                     int date = Integer.parseInt(signLogBean.getSignIn().substring(8,10));
                     DayManager.removeAbnormalDays(date);
                     DayManager.addAbnormalDays(date);
                 }
             }
             mView.showCalendar();
            }

            @Override
            public void fail(String msg) {
             mView.showMessage(msg);
            }
        });
    }

    @Override
    public void getTimeData(String token, String phone, String companyId, String year, String month, String day) {
        recordModel.getTimeDataModal(token, phone, companyId, year, month, day, new RecordContract.model.getTimeDataCallBack() {
            @Override
            public void success(SignLogBean signLogBean) {
             if (signLogBean.getIsactive() == 0){
                 mView.SetInSign("无记录");
                 mView.SetOutSign("无记录");
                 mView.SetCheckTime("请假时间： "+signLogBean.getSignIn());
             }

             else  if (signLogBean.getIsactive() != 0 &&
                     StringUtils.isNotBlank(signLogBean.getSignIn()) &&
                     StringUtils.isNotBlank(signLogBean.getSignOut())){
                 mView.SetInSign("签到时间： " + signLogBean.getSignIn());
                 mView.SetOutSign("签退时间： " + signLogBean.getSignOut());
                 mView.SetCheckTime("无记录");
             }

             else if (signLogBean.getIsactive() != 0 &&
                     StringUtils.isNotBlank(signLogBean.getSignIn()) &&
                     !StringUtils.isNotBlank(signLogBean.getSignOut())) {
                 mView.SetInSign("签到时间： " + signLogBean.getSignIn());
                 mView.SetOutSign("未签退");
                 mView.SetCheckTime("无记录");

             } else {
                 mView.SetInSign("无记录");
                 mView.SetOutSign("无记录");
                 mView.SetCheckTime("无记录");
             }
            }

            @Override
            public void fail(String msg) {
                mView.SetInSign("无记录");
                mView.SetOutSign("无记录");
                mView.SetCheckTime("无记录");
            }
        });
    }
}
