package com.person.lx.sign.login;

import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.consts.Consts;
import com.person.lx.sign.utils.JacksonUtils;

import java.util.Map;

public class LoginModelImpl implements LoginContract.LoginModel {
    @Override
    public void containLoginResponseData(String phone, String password, CallBack callBack) {
        postLogin(Consts.url+"app/login",phone,password,callBack);
    }



    private void postLogin(String url,String phone, String password,final CallBack callBack){
    OkGo.<String>post(url)
            .tag(this)
            .params("phone", phone)
            .params("password", password)
            .isMultipart(false)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
            .execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String data = response.body();

                   Map<String,Object> result = JacksonUtils.toMap(data);

                   if (result.get("code").toString().equals(Consts.SUCCESS_CODE)){
                       Map<String,Object> str = (Map<String, Object>) result.get("result");
                       callBack.loginSuccess(str.get("token").toString(),str.get("companyId").toString(),str.get("phone").toString());
                   }else {
                       callBack.loginFailed(result.get("msg").toString());
                   }

                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);

                    callBack.loginFailed(Consts.SERVRCE_ERROR);
                }
            });
}

}
