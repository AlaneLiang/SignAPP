package com.person.lx.sign.person;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.person.lx.sign.R;
import com.person.lx.sign.customView.CircleImageView;
import com.person.lx.sign.login.LoginActivity;
import com.person.lx.sign.person.company.CompanyActivity;
import com.person.lx.sign.person.info.InfoActivity;
import com.person.lx.sign.utils.ActiveActUtil;
import com.person.lx.sign.utils.ImageUtil;

import static android.content.Context.MODE_PRIVATE;

public class PersonFragment extends Fragment implements PersonContract.view, View.OnClickListener {

    private LinearLayout linearLayout;
    private View view;
    private PersonPresenter presenter;
    private CircleImageView personImg;
    private TextView personName;
    private Button signOut;
    private com.person.lx.sign.customView.item_view personInfo,personCompany,appVersion,helpFeedback;
    public PersonFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        initView();
        presenter.initData(getToken(),getCompanyId());
        return view;
    }

    public void initView(){
        linearLayout = view.findViewById(R.id.layout_person);
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        Drawable drawable =new BitmapDrawable(bm);
        linearLayout.setBackground(drawable);
        personName = view.findViewById(R.id.person_name);
        personImg = view.findViewById(R.id.person_img);
        signOut = view.findViewById(R.id.sign_out);
        personInfo = view.findViewById(R.id.person_info);
        personCompany = view.findViewById(R.id.person_company);
        appVersion = view.findViewById(R.id.app_version);
        helpFeedback = view.findViewById(R.id.help_feedback);
        signOut.setOnClickListener(this);
        personInfo.setOnClickListener(this);
        personCompany.setOnClickListener(this);
        appVersion.setOnClickListener(this);
        helpFeedback.setOnClickListener(this);
        presenter = new PersonPresenter(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * 从SharedPreferences获取存储的值
     * @param value
     * @return
     */
    private String  getFromSharedPreferences(String value){
        SharedPreferences preferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        String result = preferences.getString(value,"");
        return result;
    }
    @Override
    public String getCompanyId() {

        return getFromSharedPreferences("companyId");
    }

    @Override
    public String getToken() {
        return getFromSharedPreferences("token");
    }

    @Override
    public String getPhone() {
        return getFromSharedPreferences("phone");
    }

    @Override
    public void initData(String img, String name) {
        if (img != null){
            personImg.setImageBitmap(ImageUtil.stringToBitmap(img));
        }else {
            Bitmap bm= BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
            personImg.setImageBitmap(bm);
        }
         personName.setText(name);
    }

    public void showMessage(String msg) {
        Toast toast= Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.sign_out:
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear().commit();
                Intent login = new Intent(getActivity(),LoginActivity.class);
                startActivity(login);
                ActiveActUtil.getInstance().exit();
                break;
            case R.id.person_info:
                Intent personInfo = new Intent(getActivity(),InfoActivity.class);
                startActivity(personInfo);
                break;
            case R.id.person_company:
                Intent companyInfo = new Intent(getActivity(),CompanyActivity.class);
                startActivity(companyInfo);
                break;
            case R.id.app_version:
                showMessage("已经是最新版本");
                break;
            case R.id.help_feedback:
                break;
        }
    }
}
