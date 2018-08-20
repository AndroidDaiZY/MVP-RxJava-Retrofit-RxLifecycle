package com.reborn.skin.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by 戴震宇 on 2018/8/7 0007.
 */

public class BaseApplication extends Application{
    /**
     * 内存检测
     */
    private RefWatcher mRefWatcher;
    public  static RefWatcher getRefWatcher(Context context){
        BaseApplication application= getInstance();
        return application.mRefWatcher;
    }
    /**
     * application类型的context对象
     */
    private static BaseApplication mApplicationContext;

    public static BaseApplication getInstance(){
        return mApplicationContext;
    }
    public BaseApplication(){
        mApplicationContext=this;
    }
    /**
     * 主线程ID
     */
    public static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    public static Thread mMainThread;
    /**
     * 主线程Handler
     */
    public static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    public static Looper mMainLooper;
    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    /**
     * 初始化Tinker
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LeakCanary
        initLeakCanary();
        //初始化 logger
        initLogger();
        //初始化Bugly
        initBugly();
        //初始化换肤
        initTheme();
    }

    /**
     * 检测内存泄漏  测试下启动
     */
    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    /**
     * Logcat日志
     */
    private void initLogger(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * Bugly
     * 调试时，将第三个参数改为true
     */
    private void initBugly(){
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        //此处为热修复的初始化 一句话即可
        Bugly.init(this, "74600b168e", true);
        //bugly 异常上报
        CrashReport.initCrashReport(this,"74600b168e", true);
    }

    /**
     *  换肤
     */
    private void initTheme(){
       // SkinCompatManager.withoutActivity(this).loadSkin();
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }


}
