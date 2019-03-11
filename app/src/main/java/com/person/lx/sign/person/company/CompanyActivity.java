package com.person.lx.sign.person.company;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.person.lx.sign.R;
import com.person.lx.sign.bean.CompanyBean;
import com.person.lx.sign.utils.ImageUtil;

import java.util.Objects;

public class CompanyActivity extends AppCompatActivity implements CompanyContract.view {
    private Toolbar mToolbar;
    private CompanyPresenter companyPresenter;
    private ImageView companyImg;
    private TextView description,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        initView();
        companyPresenter.initData(getToken(),getCompanyId());
    }
    private void initView(){
        companyImg = findViewById(R.id.company_img);
        description = findViewById(R.id.description);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        companyPresenter = new CompanyPresenter(this);
        mToolbar= (Toolbar)findViewById(R.id.person_company_toolbar);
        mToolbar.setTitle("公司信息");
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
    public String getCompanyId() {

        return getFromSharedPreferences("companyId");
    }
    @Override
    public String getToken() {
        return getFromSharedPreferences("token");
    }
    @Override
    public void initData(CompanyBean companyBean) {
        if (companyBean.getIcon() != null){
            companyImg.setImageBitmap(ImageUtil.stringToBitmap(companyBean.getIcon()));
        }else {
            Bitmap bm= BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
            companyImg.setImageBitmap(bm);
        }
        description.setText("          "+companyBean.getDescription());
        phone.setText(companyBean.getPhone());
        address.setText(companyBean.getAddress());
    }
}
