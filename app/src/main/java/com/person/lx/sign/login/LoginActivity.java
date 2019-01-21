package com.person.lx.sign.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.person.lx.sign.R;
import com.person.lx.sign.utils.ImageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
   private ImageView imageView;
   private TextView textView;
    private LoginContract.Present mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //初始化界面
        initViews();
        initParams();
        initBackground();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.start();
    }

    private void  initViews(){
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

    }
    private void initParams() {
        mPresent = new LoginPresenter(this);
    }

    private void initBackground(){
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str=df.format(date);
        int a=Integer.parseInt(str);
        if (a>=0&&a<=12) {
            Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_morning_img,480,720);;
            imageView.setImageBitmap(bm);
            textView.setText("Morning");
        }
        if (a>12&&a<=18) {

            Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_night_img,480,720);;
            imageView.setImageBitmap(bm);
            textView.setText("Afternoon");
        }
        if (a>18&&a<=24) {

            Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_night_img,480,720);;
            imageView.setImageBitmap(bm);
            textView.setText("Night");
        }
    }

    @Override
    public void loginError(String msg) {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public String getAccount() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.Present presenter) {

    }
}
