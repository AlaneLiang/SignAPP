package com.person.lx.sign.map;

import com.person.lx.sign.bean.CompanyBean;

public interface MapContract {
    interface View {
        String getCompanyId();
        void initCompanyLocation(CompanyBean info);
                        }
    interface Present{
        void getCompanyInfo(String companyId);
    }
    interface Model{
        void getCompanyInfoModel(String companyId,CallBack callBack);

        interface CallBack{
            void success(CompanyBean info);
            void failed(String msg);
        }
    }
}
