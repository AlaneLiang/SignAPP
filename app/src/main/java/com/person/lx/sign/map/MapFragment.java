package com.person.lx.sign.map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.*;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.person.lx.sign.MainActivity;
import com.person.lx.sign.R;
import com.person.lx.sign.bean.CompanyBean;

import org.apache.commons.lang3.StringUtils;

import static android.content.Context.MODE_PRIVATE;


public class MapFragment extends Fragment implements MapContract.View, View.OnClickListener {
    private static  final String MapFragment_TAG = "MapFragment";
    private Context mcontext;
    private MapView mMapView = null;
    private View view;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    private BDLocation location;
    private MapContract.Present present;
    private FloatingActionButton floatbutton;
    private Button buttonInit;
    private CompanyBean companyInfo;
    private AlertDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_map, container, false);
        initView();
        initParams();
        new Thread(new Runnable() {
            @Override
            public void run() {

                initLocationOption();
            }
        }).start();

        return view;
    }
    private void initParams() {
        present = new MapPresenter(this);
        present.getCompanyInfo(getToken(),getCompanyId());


    }

    @SuppressLint("WrongViewCast")
    private void initView(){
        floatbutton= (FloatingActionButton) view.findViewById(R.id.float_button);
        buttonInit = view.findViewById(R.id.map_init);
        floatbutton.setOnClickListener(this);
        buttonInit.setOnClickListener(this);

        mMapView= (MapView) view.findViewById(R.id.map_view);
        // 不显示缩放比例尺
        mMapView.showZoomControls(true);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        mMapView.showZoomControls(false);
        //百度地图
        mBaiduMap = mMapView.getMap();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        //设置地图的缩放级别
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(15.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.float_button:
                signCheck();
                break;
            case R.id.map_init:
                mLocationClient.stop();
                initLocationOption();
                break;
        }
    }

    /**
     * 签到需要检查是否在范围内
     *
     */
    private void signCheck(){
       if (companyInfo.getLongitude()+0.002790 >= location.getLongitude() && location.getLongitude() >= companyInfo.getLongitude()-0.002790&&
                companyInfo.getLatitude()+0.002280 >= location.getLatitude() && location.getLatitude() >= companyInfo.getLatitude()-0.002280){
           present.sign(getToken(),getPhone(),getCompanyId());
       }else {
           showMsg("您不在签到范围内！");
       }
    }
    /*************************************************
     * 定时后自动消失
     */
    private static int MSG_DISMISS_DIALOG = 0;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(MSG_DISMISS_DIALOG == msg.what){
                if(null != dialog){

                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            }
        }


    };

    /*************************************************************************
     * 动画签到成功
     * ************************************************************************/
    public   void   showSucceedDialog() {
        dialog = new AlertDialog.Builder ( getActivity()).create ( );
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.succeed_dialog,null);
        Window window = dialog.getWindow ( ); //获取dialog控件
        window.setGravity ( Gravity.CENTER ); //此处可以设置dialog显示的位置
        dialog.setView ( dialogView );
        dialog.setCanceledOnTouchOutside(false);
        dialog.show ( );
        mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, 5000);

    }


    public void showSucceedOutDialog() {
        dialog = new AlertDialog.Builder ( getActivity()).create ( );
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.succeed_out_dialog,null);
        Window window = dialog.getWindow ( ); //获取dialog控件
        window.setGravity ( Gravity.CENTER ); //此处可以设置dialog显示的位置
        dialog.setView ( dialogView );
        dialog.setCanceledOnTouchOutside(false);
        dialog.show ( );
        mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, 5000);

    }
    public void  showMsg(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化公司位置
     * @param info
     */
    public void covered(CompanyBean info) {                  //标注覆盖物方法
        if (info != null){
            this.companyInfo = info;
        }else {
           getActivity().finish();
           return ;
        }

        double Latitude = info.getLatitude();
        double Longitude = info.getLongitude();

        //定义Maker坐标点
        //mBaiduMap.clear();
        LatLng point = new LatLng(Latitude,Longitude);
        //构建Marker图标
        //CircleOptions circleOptions=new CircleOptions();
        // circleOptions.center(point).radius(140).fillColor(getResources().getColor(R.color.map));
        OverlayOptions ooCircle = new CircleOptions().fillColor(0xAAe51c23)
                .center(point)
                .radius(150);
        mBaiduMap.addOverlay(ooCircle);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.company_address);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }
    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {

        //定位初始化
        mLocationClient = new LocationClient(getActivity().getApplicationContext());

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);


        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();


    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
        this.mcontext = context;
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mcontext = activity;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /*
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        //do something
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    public  void shoWarning(String title,String content){

    }
    public void setLocation(BDLocation location) {
        if(location==null){
            shoWarning("无法定位","请检查您的位置权限或者点击定位按钮！以确保程序获得您的位置信息！");

        }else
        {
            this.location = location;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
            mLocationClient.stop();
            mMapView.onPause();
        }else {
            //Fragment显示时调用
            mLocationClient.start();
            mMapView.onResume();

        }

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
    public void initCompanyLocation(CompanyBean info) {

    }



    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location) {
              setLocation(location);
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            MyLocationConfiguration mLocationConfiguration =
                    new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,false,null);
            mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);
        }

    }
}
