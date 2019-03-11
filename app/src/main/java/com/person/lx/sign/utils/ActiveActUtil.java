package com.person.lx.sign.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LX on 2017-03-07.
 * activity优化工具类
 * 在activity中使用以下代码
 * ActiveActUtil.getInstance().addActivity(this);
 * exit
 * 清空所有activity
 */

public class ActiveActUtil {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActiveActUtil instance;
    private ActiveActUtil() {
    }
    // 单例模式中获取唯一的MyApplication实例
    public static ActiveActUtil getInstance() {
        if (null == instance) {
            instance = new ActiveActUtil();
        }
        return instance;
    }
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        instance = null;
    }
}
