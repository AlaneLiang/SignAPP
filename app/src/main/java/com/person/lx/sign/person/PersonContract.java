package com.person.lx.sign.person;

import com.person.lx.sign.bean.PersonInfoBean;

public interface PersonContract {
    interface  view {
        String getCompanyId();
        String getToken();
        String getPhone();

        void initData(String img,String name);
    }

    interface presenter{
      void initData(String token,String companyId);
    }

    interface model{
        void initDataModel(String token,String companyId,initCallBack callBack);
        interface initCallBack{
            void success(String img,String name);
            void fail(String msg);
        }
    }
}
