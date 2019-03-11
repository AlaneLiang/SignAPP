package com.person.lx.sign.person.info;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.R;
import com.person.lx.sign.consts.Consts;
import com.person.lx.sign.login.LoginActivity;
import com.person.lx.sign.utils.ActiveActUtil;
import com.person.lx.sign.utils.StrUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordReset extends DialogFragment implements View.OnClickListener {
    private Dialog mFactRstPwsCheckDialog;
    private Button cancelButton,confirmButton;
    private FragmentActivity activity;
    private AlertDialog dialog;
    private View view;
    private Bundle mBundle;
    private String token;
    private AppCompatEditText oldPassword,newPassword,confirmPassword;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activity = getActivity();
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.reset_password_dialog, null);
        Init();
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void Init() {
        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmPassword = view.findViewById(R.id.confirm_password);
        cancelButton = view.findViewById(R.id.DialogCancel);
        confirmButton = view.findViewById(R.id.DialogConfirm);
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        mBundle= getArguments();
        if (mBundle !=null){
            this.token=mBundle.getString("token");


        }
    }

     private Boolean checkPassword(String oldPassword,String newPassword,String confirmPassword){
        if (StringUtils.isBlank(token)){
            showmessage("请重新登录，token错误");
            return false;
        }
        if (StringUtils.isBlank(oldPassword)){
            showmessage("旧密码不为空！");
            return false;
        }

         if (StringUtils.isBlank(newPassword)){
             showmessage("请输入新密码！");
             return false;
         }

         if (StringUtils.isBlank(confirmPassword)){
             showmessage("请输入新密码！");
             return false;
         }

         if (!newPassword.equals(confirmPassword)){
             showmessage("两次新密码不一致！");
             return false;
         }

         String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!StrUtils.match(regex,newPassword)){
            showmessage("密码必须为8个以上大小写加数字");
            return false;
        }

      return true;
     }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DialogCancel:
                dismiss();
                break;
            case R.id.DialogConfirm:
                editorPassword();
                break;
        }

    }


    private void editorPassword(){

        if (checkPassword(getOldPassword(),getNewPassword(),getConfirmPassword())){
               updatePassword(token,getOldPassword(),getConfirmPassword());
        }

    }

    private void updatePassword(String token,String oldPassword,String newPassword) {

        OkGo.<String>post(Consts.url+"app/reset/password")
                .tag(this)
                .headers("Token",token)
                .params("oldPassword",oldPassword)
                .params("newPassword",newPassword)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){

                            showmessage(json.get("msg").getAsString());

                            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.clear().commit();
                            Intent login = new Intent(getActivity(),LoginActivity.class);
                            startActivity(login);
                            ActiveActUtil.getInstance().exit();


                        }else {
                            showmessage(json.get("msg").getAsString());

                        }
                    }
                });
    }

    private String getOldPassword(){
        return oldPassword.getText().toString();
    }

    private String getNewPassword(){
        return newPassword.getText().toString();
    }
    private String getConfirmPassword(){
        return confirmPassword.getText().toString();
    }

    public void showmessage(String msg) {
        Toast toast= Toast.makeText(activity,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
