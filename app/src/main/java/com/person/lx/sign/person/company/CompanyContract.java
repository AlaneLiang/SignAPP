package com.person.lx.sign.person.company;

import com.person.lx.sign.bean.CompanyBean;

public interface CompanyContract {
    interface view{
        String getCompanyId();
        String getToken();

        void initData(CompanyBean companyBean);
    }

    interface presenter{
        void initData(String token,String companyId);
    }

    interface model{
        void initDataModel(String token,String companyId,getCallBack callBack);

        interface getCallBack{
            void success(CompanyBean companyBean);
            void fail(String msg);
        }
    }
}
