package com.person.lx.sign.record.dialog;

import com.person.lx.sign.record.RecordContract;

public class EditorDialogPresenter implements DialogContract.presenter {
    private DialogContract.view mView ;

    private EditorDialogModel editorDialogModel;
    EditorDialogPresenter(DialogContract.view view){
        this.mView = view;
        editorDialogModel = new EditorDialogModel();
    }


    @Override
    public void updateMessage(String token, String phone, String companyId, String year, String month, String day, String msg) {
        editorDialogModel.updateMessageModel(token, phone, companyId, year, month, day, msg, new DialogContract.model.updateCallBack() {
            @Override
            public void success(String msg) {
            mView.showmessage(msg);
            }

            @Override
            public void fail(String msg) {
                mView.showmessage(msg);
            }
        });
    }
}
