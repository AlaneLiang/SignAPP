package com.person.lx.sign.map;

import com.person.lx.sign.bean.CompanyBean;

public interface MapContract {

    interface View {
        String getCompanyId();
        String getToken();
        String getPhone();
        void covered(CompanyBean info);
        void initCompanyLocation(CompanyBean info);
        void   showSucceedDialog();
        void showSucceedOutDialog();
        void showMsg(String msg);
        }


    interface Present{
        void getCompanyInfo(String token,String companyId);
        void sign(String token,String phone,String companyId);
    }


    interface Model{
        void getCompanyInfoModel(String token,String companyId,CallBack callBack);
        void signModel(String token,String phone,String companyId,signCallBack callBack);

        interface CallBack{
            void success(CompanyBean info);
            void failed(String msg);
        }

        interface signCallBack{
            void success(String msg);
            void failed(String msg);
        }
    }
}
