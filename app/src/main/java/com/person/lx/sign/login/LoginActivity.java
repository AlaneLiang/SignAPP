package com.person.lx.sign.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.person.lx.sign.MainActivity;
import com.person.lx.sign.R;
import com.person.lx.sign.utils.ActiveActUtil;
import com.person.lx.sign.utils.ImageUtil;
import com.person.lx.sign.utils.JacksonUtils;

import net.qiujuer.genius.blur.StackBlur;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {
   private ImageView imageView;
   private TextView textView,forgetPassword,saying;
    private LoginContract.Present mPresent;
    private Button signButton;
    private EditText phoneNumber,password;
    private ProgressDialog dialog;
    private SharedPreferences sp;

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
        ActiveActUtil.getInstance().addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.start();
    }

    private void  initViews(){
        imageView = findViewById(R.id.imageView);
        forgetPassword = findViewById(R.id.forget_password);
        textView = findViewById(R.id.textView);
        phoneNumber = findViewById(R.id.phone_number);
        saying = findViewById(R.id.saying);
        password = findViewById(R.id.password);
        signButton = findViewById(R.id.sign_button);
        signButton.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        sp=this.getSharedPreferences("data",MODE_PRIVATE);



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
             //Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_morning_img,480,720);;
//            imageView.setImageBitmap(bm);
            Bitmap drawable=setPictureBlur(19,R.drawable.good_morning_img);
            imageView.setImageBitmap(drawable);

            textView.setText("Morning");
            saying.setText("美好的一天，从这里开始！");
        }
        if (a>12&&a<=18) {

            //Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_night_img,480,720);;
            //imageView.setImageBitmap(bm);
            Bitmap drawable=setPictureBlur(19,R.drawable.good_night_img);
            imageView.setImageBitmap(drawable);
            textView.setText("Afternoon");
            saying.setText("忙了一上午了，记得放松一下哦！");
        }
        if (a>18&&a<=24) {

           // Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.good_night_img,480,360);
            //imageView.setImageBitmap(bm);
            Bitmap drawable=setPictureBlur(19,R.drawable.good_night_img);
            imageView.setImageBitmap(drawable);
            textView.setText("Night");
            saying.setText("忙碌一天了，听一首音乐放松一下！");
        }
    }

    /*设置背景图片模糊

     *http://blog.csdn.net/fan7983377/article/details/51568059
     * 1.加入依赖   compile 'net.qiujuer.genius:blur:2.0.0-beta4'
     *可以通过改变radius的值来改变模糊度，值越大，模糊度越大，radius<=0时则图片不显示；一般radius的值以20左右为佳！
     * */

    public Bitmap setPictureBlur(int radius, int pictuer){
        Bitmap mBitmap= BitmapFactory.decodeResource(getResources(),pictuer);
        Bitmap newBitmap= StackBlur.blurNatively(mBitmap,radius, false);

        return  newBitmap;



    }

    @Override
    public void loginError(String msg) {
        dialog.dismiss();
              show(msg);
    }
    private  void storge(String key,String value){
    SharedPreferences.Editor editor =sp.edit();
    //通过putString()方法，将数据存入文件中
    editor.putString(key,value);
      //用commit()方法予以正式提交
        editor.commit();
}
    @Override
    public void loginSuccess(String token,String companyId,String phone) {
        dialog.dismiss();
        storge("token",token);
        storge("companyId",companyId);
        storge("phone",phone);
        Intent intent = new Intent();
        //setClass函数的第一个参数是一个Context对象
        //Context是一个类，Activity是Context类的子类，也就是说，所有的Activity对象，都可以向上转型为Context对象
        //setClass函数的第二个参数是一个Class对象，在当前场景下，应该传入需要被启动的Activity类的class对象
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        ActiveActUtil.getInstance().exit();
    }

    @Override
    public String getPhone() {
        if (phoneNumber.getText().toString().isEmpty()){
            show("请输入电话号码！");
            return null;
        }
        return phoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        if (password.getText().toString().isEmpty()){
            show("请输入密码！");
           return null;
        }
        return password.getText().toString();
    }

    @Override
    public void setPresenter(LoginContract.Present presenter) {

    }
    private void show (String args){
        Toast.makeText(this,args,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.sign_button:
                checkSign();
                break;
            case R.id.forget_password:
                show("请联系管理员重置密码！");
                break;
        }
    }
    public void checkSign(){
        if (StringUtils.isNotBlank(getPhone())&&StringUtils.isNotBlank(getPassword())){
            mPresent.login(getPhone(),getPassword());
            dialog= ProgressDialog.show(LoginActivity.this, "", "登录中，请稍后…");
        }

    }
}
