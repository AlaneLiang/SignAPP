package com.person.lx.sign.record;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.bean.SignLogBean;
import com.person.lx.sign.consts.Consts;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RecordModel implements RecordContract.model {
    @Override
    public void initDataModal(String token, String phone, String companyId, String year, String month, initCallBack callBack) {
        getInitData(Consts.url+"app/record/init",token,phone,companyId,year,month,callBack);
    }

    @Override
    public void getTimeDataModal(String token, String phone, String companyId, String year, String month, String day, getTimeDataCallBack callBack) {
        getTimeData(Consts.url+"app/get/data",token,phone,companyId,year,month,day,callBack);
    }

    private void getTimeData(String url, String token, String phone, String companyId, String year, String month, String day, final getTimeDataCallBack callBack){
        OkGo.<String>post(url)
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .params("phone",phone)
                .params("year",year)
                .params("month",month)
                .params("day",day)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){

                            Gson gson = new Gson();
                            SignLogBean signLogBean = gson.fromJson(json.get("result"), SignLogBean.class);
                            callBack.success(signLogBean);

                        }else {
                            callBack.fail(json.get("msg").getAsString());
                        }
                    }
                });
    }

    private void getInitData(String url,String token, String phone, String companyId, String year, String month,final initCallBack callBack){
        OkGo.<String>post(url)
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .params("phone",phone)
                .params("year",year)
                .params("month",month)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){

                            Gson gson = new Gson();
                            List<SignLogBean> signLogBeanArrayList = new ArrayList<>();
                            JsonArray jsonArray = json.get("result").getAsJsonArray();

                            for (JsonElement signLog : jsonArray) {
                                //使用GSON，直接转成Bean对象
                                SignLogBean signLogBean = gson.fromJson(signLog, SignLogBean.class);
                                signLogBeanArrayList.add(signLogBean);
                            }

                            callBack.success(signLogBeanArrayList);

                        }else {
                            callBack.fail(json.get("msg").getAsString());
                        }
                    }
                });
    }
}
