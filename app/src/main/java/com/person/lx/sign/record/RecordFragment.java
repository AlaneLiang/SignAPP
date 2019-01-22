package com.person.lx.sign.record;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import com.person.lx.sign.R;
import com.person.lx.sign.customView.CalendarView;
import com.person.lx.sign.customView.DayManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecordFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private View view;
    private TextView tv_pre;
    private TextView tv_month;
    private TextView tv_next;
    private TextView in_time;
    private  TextView out_time,check_time;
    /**日历控件*/
    private CalendarView calendar;
    /**日历对象*/
    private Calendar cal;
    /**格式化工具*/
    private SimpleDateFormat formatter;
    /**日期*/
    private Date curDate;

    private String year;
    private String month;
    private  String day;
    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);
        this.context=getActivity();
        initView();
        GetYM();//初始化数据
        System.out.println("YEAR******"+year+"MOnth*********"+month+"**********DAY"+day);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }
    private void initView() {
        tv_pre = (TextView) view.findViewById(R.id.tv_pre);
        tv_month = (TextView)view.findViewById(R.id.tv_month);
        tv_next = (TextView) view.findViewById(R.id.tv_next);
        calendar = (CalendarView) view.findViewById(R.id.calendar);
        in_time= (TextView) view.findViewById(R.id.calendar_in_time);
        out_time= (TextView) view.findViewById(R.id.calendar_out_time);
        check_time= (TextView) view.findViewById(R.id.calendar_check_time);
        cal = Calendar.getInstance();


        //初始化界面
        init();
        // System.out.println(a.getTime()+"************data*****"+String.valueOf(a.getDate())+"**********Month*****"+a.getMonth()+1);
        calendar.setOnSelectChangeListener(new CalendarView.OnSelectChangeListener() {
            @Override
            public void selectChange(CalendarView calendarView, Date date) {
                GetClickYMD(date);
                //mDetailPersenter.CheckTime();
                System.out.println("********Y"+year+"****mon*****"+month+"**********day*****"+day);//1494480729838************data*****11**********Month*****4(month+1)

            }

        });

        //给左右月份设置点击监听事件
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);


    }

    //赋值给全局变量
    public void GetYM(){
        Date date=cal.getTime();
        this.year="20"+String.valueOf(date.getYear()).substring(1,3);
        String  mon=""+String.valueOf(date.getMonth()+1);

        if (mon.length()==1){
            this.month="0"+mon;
        }else {
            this.month=mon;
        }


    }

    /*
        点击初始全局变量
        * */
    public  void  GetClickYMD(Date date){

        this.year="20"+String.valueOf(date.getYear()).substring(1,3);
        String  mon=""+String.valueOf(date.getMonth()+1);
        String da=String.valueOf(date.getDate());
        if (mon.length()==1){
            this.month="0"+mon;
        }else {
            this.month=mon;
        }
        if (da.length()==1){
            this.day="0"+da;
        }else {
            this.day=da;
        }
    }
    /**
     * 初始化界面
     */
    private void init() {
        formatter = new SimpleDateFormat("yyyy年MM月");
        //获取当前时间
        curDate = cal.getTime();
        String str = formatter.format(curDate);
        tv_month.setText(str);
        String strPre=(cal.get(Calendar.MONTH))+"月";
        if (strPre.equals("0月")){
            strPre="12月";
        }
        tv_pre.setText(strPre);
        String strNext=(cal.get(Calendar.MONTH)+2)+"月";
        if(strNext.equals("13月")){
            strNext="1月";
        }
        tv_next.setText(strNext);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd");
        Date curDate = new Date(System.currentTimeMillis());
        final String Systemtime = formatter1.format(curDate);
        this.day=Systemtime.substring(8,10);

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pre:
                cal.add(Calendar.MONTH,-1);
                //DayManager.imitateData();
                init();
                GetYM();

                //mDetailPersenter.init();
               showCalendar();
                calendar.postInvalidate();

                break;
            case R.id.tv_next:
                cal.add(Calendar.MONTH,+1);
                DayManager.imitateData();
                init();
                GetYM();
                //mDetailPersenter.init();
                showCalendar();
                break;

        }

    }

    public void showCalendar() {
        calendar.setCalendar(cal);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
