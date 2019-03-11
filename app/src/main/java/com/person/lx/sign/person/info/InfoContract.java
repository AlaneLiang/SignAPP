package com.person.lx.sign.person.info;

import com.person.lx.sign.bean.PersonDeatilBean;

public interface InfoContract {
    interface view{
        String getToken();
        void initData(PersonDeatilBean info);
        void showMessage(String msg);
    }

    interface presenter{
        void initData(String token);
    }

    interface model{
        void initDataModel(String token,getInfoCallBack callBack);
        interface getInfoCallBack{

            void success(PersonDeatilBean personDeatil);
            void fail(String msg);
        }
    }
}
