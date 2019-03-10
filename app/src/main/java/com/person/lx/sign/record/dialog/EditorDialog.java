package com.person.lx.sign.record.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.person.lx.sign.R;

public class EditorDialog extends DialogFragment implements DialogContract.view {

    int mNum;
    private Bundle mBundle;
    private String year;
    private String month;
    private String day;
    private  String companyId,token,phone;
    private EditText mEditText;
    private String content=null;
    private FragmentActivity activity;
    private AlertDialog dialog;
    private EditorDialogPresenter editorDialogPresenter = new EditorDialogPresenter(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Init();
        //可以用下面的方法得到参数
//      mNum = getArguments().getInt("num");
        activity = getActivity();
        LayoutInflater mInflater = LayoutInflater.from(activity);
        View v = mInflater.inflate(R.layout.dialog_fragment_editor, null);
        mEditText = (EditText) v.findViewById(R.id.add_content);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("提交"
                        , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (!mEditText.getText().toString().isEmpty()){
                                    content = mEditText.getText().toString();
                                    //mDialogPersenterIml.updateMessage();//上传数据
                                    editorDialogPresenter.updateMessage(getToken(),getPhone(),getCompanyId(),getYear(),getMonth(),getDay(),getMessage());

                                }else {
                                    showmessage("message不为空！");
                                }


                            }
                        }
                )
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    public void  Init(){
        mBundle=getArguments();
        if (mBundle!=null){
            this.year=mBundle.getString("year");
            this.month=mBundle.getString("month");
            this.day=mBundle.getString("day");
            this.token=mBundle.getString("token");
            this.companyId=mBundle.getString("companyId");
            this.phone=mBundle.getString("phone");

        }



    }

    @Override
    public String getYear() {
        return year;
    }

    @Override
    public String getMonth() {
        return month;
    }

    @Override
    public String getDay() {
        return day;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getMessage() {
        return content;
    }

    @Override
    public void showmessage(String msg) {
        Toast toast= Toast.makeText(activity,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void showSuccess() {

    }
}
