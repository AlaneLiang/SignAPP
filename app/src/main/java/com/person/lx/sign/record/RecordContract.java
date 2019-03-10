package com.person.lx.sign.record;

import com.person.lx.sign.bean.SignLogBean;

import java.util.List;

public interface RecordContract {
    interface  view{
        String getDay();
        String getYear();
        String getMonth();
        String getCompanyId();
        String getToken();
        String getPhone();
        void showCalendar();
        void  SetInSign(String misSign);
        void  SetOutSign(String misSign);
        void  SetCheckTime(String misSign);//审核通过时间
        void showMessage(String msg);
    }

    interface presenter{
        void initData(String token,String phone,String companyId,String year,String month);

        void getTimeData(String token,String phone,String companyId,String year,String month,String day);
    }

    interface model{

        void initDataModal(String token,String phone,String companyId,String year,String month,initCallBack callBack);

        void getTimeDataModal(String token,String phone,String companyId,String year,String month,String day,getTimeDataCallBack callBack);

        interface initCallBack{
            void success(List<SignLogBean> list);
            void fail(String msg);
        }

        interface getTimeDataCallBack{
            void success(SignLogBean signLogBean);
            void fail(String msg);
        }
    }
}
