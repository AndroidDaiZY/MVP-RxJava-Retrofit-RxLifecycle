package com.reborn.skin.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.reborn.skin.listener.LifeCycleListener;
import com.reborn.skin.utils.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 * 基类 FragmentActivity
 *  统一管理Activity所需配置 初始化
 *  FragmentActivity 为了适配3.0一下没有fragment api
 */
public abstract class BaseFragmentActivity extends RxFragmentActivity {
    protected View mContentView;
    protected Context mContext;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        mContext=this;
        ActivityManager.getInstance().addActivity(this);
        // 1.初始化上个页面传入数据
        initBundleData();
        //2.调用setContentView 加载布局
        mContentView = convertView();
        if (mContentView == null) {
            throw new ClassCastException("setLayout 只能是View 或者布局id");
        }
        setContentView(mContentView);
        //沉浸式 状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        //3.做一些通用的操作 用注解绑定View(黄油刀)
        unBinder = ButterKnife.bind(this);
        //4. 初始化View
        initView(savedInstanceState);
        //5.初始化数据
        initData();
    }
    /**
     * 对View 进行判断
     * @return view
     */
    private View convertView() {
        View contentView =null;
        if (getLayout() instanceof Integer){
            Integer layoutId= (Integer) getLayout();
            contentView=getLayoutInflater().inflate(layoutId,null,false);
        }
        if (getLayout() instanceof View){
            contentView= (View) getLayout();
        }
        return contentView;
    }
    /**
     * 页面的布局ID 或者View
     */
    protected abstract Object getLayout();
    /**
     * 获取上一个界面传送过来的数据
     */
    protected  abstract void initBundleData();
    /**
     * 初始化view
     */
    protected abstract void initView(Bundle savedInstanceState);
    /**
     * 初始化Data
     */
    protected abstract  void initData();

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityManager.getInstance().removeActivity(this);
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

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }
}
