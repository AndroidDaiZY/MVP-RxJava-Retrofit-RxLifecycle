package com.reborn.skin.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.Stack;

/**
 * Created by 戴震宇 on 2018/8/7 0007.
 * Activity 管理类
 */

public class ActivityManager {
    private static Stack<Activity>activityStack;
    private static volatile ActivityManager instance = null;
    private ActivityManager(){}
    /**
     * 单一实例
     */
    public static synchronized ActivityManager getInstance(){
        if (instance == null){
            synchronized (ActivityManager.class){
                instance=new ActivityManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity 到堆栈
     *
     */
    public synchronized void addActivity(Activity activity){
        if (activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 移除最后一个Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            Logger.i( "ActivityManager移除了：" + activity.getClass().getName());
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            Logger.i( "ActivityManager关闭了：" + activity.getClass().getName());
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                removeActivity(activityStack.get(i));
                return;
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            //清除通知栏
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
