package com.reborn.skin.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.reborn.skin.listener.LifeCycleListener;
import com.reborn.skin.utils.ActivityManager;
import com.trello.rxlifecycle2.components.RxActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 * Activity 基类
 *   ActivityManager: 管理 Activity 进程
 *   RxActivity:管理 RxJava 与Activity的生命周期 绑定
 *
 *
 */
public abstract class BaseActivity extends RxActivity {
    /**
     * Layout 布局
     */
    protected View mContextView;
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 黄油刀
     */
    protected Unbinder unBinder;
    /**
     * 沉浸式
     */
    private ImmersionBar mImmersionBar;
    /**
     * 生命周期监听
     */
    public LifeCycleListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( mListener !=null){
            mListener.onCreate(savedInstanceState);
        }
        mContext=this;
        ActivityManager.getInstance().addActivity(this);//将当前Activity 添加进入管理栈

        //1.初始化上个页面传入数据
        initBundleData();
        //2.调用setContentView 加载布局
        mContextView=convertView();
        if (mContextView == null){
            throw new ClassCastException("空布局");
        }
        setContentView(mContextView);
        //沉浸式 状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        //3.黄油刀 初始化控件
        unBinder= ButterKnife.bind(this);
        //4.做一些View 的初始化操作
        initView(savedInstanceState);
        //5.初始化数据
        initData();
    }

    private View convertView(){
        View contentView = null;
        if (getLayout() instanceof Integer){
            Integer layoutId= (Integer) getLayout();
            contentView=getLayoutInflater().inflate(layoutId,null,false);
        }
        if (getLayout() instanceof View){
            contentView = (View) getLayout();
        }
        return contentView;
    }
    /**
     * 页面的布局ID 或者View
     */
    protected abstract Object getLayout();
    /**
     * 1.获取 上个页面传过来的数据
     */
    protected abstract void initBundleData();

    /**
     * 2.初始化View
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 3.初始化数据
     */
    protected abstract void initData();
    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener!=null){
            mListener.onStart();
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    /**
     * 资源释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityManager.getInstance().removeActivity(this);//将当前activity移除管理栈
        if (mImmersionBar != null){
            mImmersionBar.destroy();//必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }
    /**
     * 打开Activity
     */
    protected void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 打开Activity
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 获取当前的Activity
     */
    protected Activity getAppActivity() {
        return this;
    }
}
