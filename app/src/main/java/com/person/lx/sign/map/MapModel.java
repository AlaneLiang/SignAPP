package com.person.lx.sign.map;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.person.lx.sign.bean.CompanyBean;
import com.person.lx.sign.consts.Consts;
import com.person.lx.sign.utils.JacksonUtils;

import java.util.Map;

public class MapModel implements MapContract.Model {

    @Override
    public void getCompanyInfoModel(String token,String companyId, CallBack callBack) {

        getComanyInfoOkGo(Consts.url+"app/company/info",companyId,token,callBack);

    }

    @Override
    public void signModel(String token, String phone, String companyId, signCallBack callBack) {
            signData(Consts.url+"app/map/sign",token,phone,companyId,callBack);
    }

    private void signData(String url,String token, String phone, String companyId, final signCallBack callBack){
        OkGo.<String>post(url)
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .params("phone",phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){
                            callBack.success(json.get("result").getAsString());
                        }else {
                            callBack.failed(json.get("msg").getAsString());
                        }
                    }
                });
      }

    private void getComanyInfoOkGo(String url,String companyId,String token,final CallBack callBack){
        OkGo.<String>get(url)
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
                            callBack.failed(result.get("msg").toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callBack.failed(Consts.SERVRCE_ERROR);
                    }

                });
    }
}
