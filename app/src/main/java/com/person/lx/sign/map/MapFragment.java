package com.person.lx.sign.map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.*;
import com.baidu.mapapi.map.*;
import com.person.lx.sign.R;


public class MapFragment extends Fragment {
    private static  final String MapFragment_TAG = "MapFragment";
    private Context mcontext;
    private MapView mMapView = null;
    private View view;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    private BDLocation location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);
        initView();
        initLocationOption();
        return view;
    }

    private void initView(){
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
