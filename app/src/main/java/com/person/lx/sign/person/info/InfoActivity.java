package com.person.lx.sign.person.info;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.person.lx.sign.R;
import com.person.lx.sign.bean.PersonDeatilBean;
import com.person.lx.sign.customView.CircleImageView;
import com.person.lx.sign.utils.ActiveActUtil;
import com.person.lx.sign.utils.ImageUtil;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity implements InfoContract.view, View.OnClickListener {
    private Toolbar mToolbar;
    private TextView workId,personName,personCompany,personDepartment,
            personPhone,personEmail;
    private InfoPresenter infoPresenter;
    private CircleImageView personImg;
    private Button resetPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        //initState();
        ActiveActUtil.getInstance().addActivity(this);
        infoPresenter.initData(getToken());
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void initView(){
        workId = findViewById(R.id.person_work_id);
        personName = findViewById(R.id.person_name);
        personCompany = findViewById(R.id.person_company);
        personDepartment = findViewById(R.id.person_department);
        personPhone = findViewById(R.id.person_phonenumber);
        personEmail = findViewById(R.id.person_email);
        personImg = findViewById(R.id.person_img);
        resetPasswordButton = findViewById(R.id.person_editor_password);
        resetPasswordButton.setOnClickListener(this);
        infoPresenter = new InfoPresenter(this);
        mToolbar= (Toolbar)findViewById(R.id.person_info_toolbar);
        mToolbar.setTitle("个人信息");
        setSupportActionBar(mToolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 从SharedPreferences获取存储的值
     * @param value
     * @return
     */
    private String  getFromSharedPreferences(String value){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        String result = preferences.getString(value,"");
        return result;
    }


    @Override
    public String getToken() {
        return getFromSharedPreferences("token");
    }

    @Override
    public void initData(PersonDeatilBean info) {
        workId.setText(info.getWorkId());
        personName.setText(info.getUsername());
        personCompany.setText(info.getCompanyName());
        personDepartment.setText(info.getDepartmentName());
        personPhone.setText(info.getPhone());
        personEmail.setText(info.getEmail());

        if (info.getImg() != null){
            personImg.setImageBitmap(ImageUtil.stringToBitmap(info.getImg()));
        }else {
            Bitmap bm= BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
            personImg.setImageBitmap(bm);
        }

    }

    public  void showMessage(String msg){
        Toast toast= Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_editor_password:
                PasswordReset pr = new PasswordReset();
                Bundle bundle=new Bundle();
                bundle.putString("token",getToken());
                pr.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                // 把碎片添加到碎片中
                transaction.add(pr,"tag");
                transaction.commit();
                break;
        }
    }
}
