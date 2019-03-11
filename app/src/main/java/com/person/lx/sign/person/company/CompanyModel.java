package com.person.lx.sign.person.company;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.bean.CompanyBean;
import com.person.lx.sign.consts.Consts;
import com.person.lx.sign.utils.JacksonUtils;

import java.util.Map;

public class CompanyModel implements CompanyContract.model {
    @Override
    public void initDataModel(String token, String companyId, final getCallBack callBack) {
        OkGo.<String>get(Consts.url+"app/company/info")
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        Map<String,Object> result = JacksonUtils.toMap(data);

                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);


                        if (result.get("code").toString().equals(Consts.SUCCESS_CODE)){
                            JsonObject jsonObject=json.get("result").getAsJsonObject();
                            Gson gs  = new Gson();
                            CompanyBean companyBean = gs.fromJson(jsonObject.toString(),CompanyBean.class);
                            callBack.success(companyBean);
                        }else {
                            callBack.fail(result.get("msg").toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callBack.fail(Consts.SERVRCE_ERROR);
                    }

                });
    }
}
