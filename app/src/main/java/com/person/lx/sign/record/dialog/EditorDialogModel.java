package com.person.lx.sign.record.dialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.bean.SignLogBean;
import com.person.lx.sign.consts.Consts;

public class EditorDialogModel implements DialogContract.model {
    @Override
    public void updateMessageModel(String token, String phone, String companyId, String year, String month, String day, String msg, final updateCallBack callBack) {
        OkGo.<String>post(Consts.url+"app/update/message")
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .params("phone",phone)
                .params("year",year)
                .params("month",month)
                .params("day",day)
                .params("message",msg)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){

                            callBack.success(json.get("msg").getAsString());

                        }else {
                            callBack.fail(json.get("msg").getAsString());
                        }
                    }
                });
    }


}
