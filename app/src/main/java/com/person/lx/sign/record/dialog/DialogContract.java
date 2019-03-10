package com.person.lx.sign.record.dialog;

public interface DialogContract {
    interface view {
        String getYear();
        String getMonth();
        String getDay();
        String getCompanyId();
        String getToken();
        String getPhone();
        String getMessage();
        void showmessage(String msg);
        void showSuccess();
    }

    interface presenter{
        void updateMessage(String token,String phone,String companyId,String year,String month,String day,String msg);
    }

    interface model{
        void updateMessageModel(String token,String phone,String companyId,String year,String month,String day,String msg,updateCallBack callBack);
        interface updateCallBack{
            void success(String msg);
            void fail(String msg);
        }

    }
}
