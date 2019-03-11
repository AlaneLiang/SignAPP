package com.person.lx.sign.person.info;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.bean.PersonDeatilBean;
import com.person.lx.sign.consts.Consts;

public class InfoModel implements InfoContract.model {
    @Override
    public void initDataModel(String token, final getInfoCallBack callBack) {
        OkGo.<String>post(Consts.url+"app/person/detail")
                .tag(this)
                .headers("Token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){
                            Gson gson = new Gson();
                            JsonObject info = json.get("result").getAsJsonObject();
                            PersonDeatilBean personDeatil = gson.fromJson(info.toString(),PersonDeatilBean.class);
                            callBack.success(personDeatil);

                        }else {
                            callBack.fail(json.get("msg").getAsString());
                        }
                    }
                });
    }
}
