package com.person.lx.sign;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.person.lx.sign.adapter.SectionsPagerAdapter;
import com.person.lx.sign.map.MapFragment;
import com.person.lx.sign.person.PersonFragment;
import com.person.lx.sign.record.RecordFragment;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    private ViewPager viewPager;
    private List<Fragment> fragments;
   private BottomNavigationView navigation;

    private  Fragment  currentFragment=new Fragment();;
    private   MapFragment  mapFragment = new MapFragment();
    private    RecordFragment recordFragment = new RecordFragment();
    private   PersonFragment personFragment = new PersonFragment();

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_sign:
                   viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_recorder:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_person:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
        getPersimmions();//andrid m特有设置


    }

    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViewPager();
    }
   

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        fragments = new ArrayList<Fragment>();
        fragments.add(mapFragment);
        fragments.add(recordFragment);
        fragments.add(personFragment);

        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
       if (i == 0){
           navigation.setSelectedItemId(R.id.nav_sign);
       }else if (i == 1){
           navigation.setSelectedItemId(R.id.nav_recorder);
       }else {
           navigation.setSelectedItemId(R.id.nav_person);
       }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }



    /*

     * 动态权限设置（定位必须设置）
     * */
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
