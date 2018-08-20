package com.reborn.skin.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reborn.skin.application.BaseApplication;
import com.reborn.skin.listener.LifeCycleListener;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 * 基类Fragment
 * 备注:所有的Fragment都继承自此Fragment
 * 1.规范团队开发
 * 2.统一处理Fragment所需配置,初始化
 *
 */
public abstract  class BaseFragment extends RxFragment {

    /**
     *  用户设置的ContentView
     */
    protected View mContentView;
    /**
     * 黄油刀
     */
    private Unbinder unbinder;
    /**
     * View  是否加载
     */
    protected boolean isViewInitiated;
    /**
     * 页面是否可见
     */
    protected  boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;
    /**
     * 宿主Activity
     */
    protected Activity mActivity;

    public LifeCycleListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mListener != null){
            mListener.onAttach(activity);
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        initBundleData(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onCreateView(inflater, container, savedInstanceState);
        }
        mContentView=createContentView(container);
        if (mContentView.getParent() != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        unbinder= ButterKnife.bind(this,mContentView);
        return mContentView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener != null) {
            mListener.onActivityCreated(savedInstanceState);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isViewInitiated){
            initView(view);
            initData();
        }
        isViewInitiated=true;
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        loadData();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener.onDetach();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null!=unbinder){
            unbinder.unbind();
        }
        if ( mListener != null){
            mListener.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        BaseApplication.getRefWatcher(getActivity()).watch(this);
    }

    /**
     * 创建View
     */
    private View createContentView(ViewGroup parent) {
        Object layout = getContentLayout();
        View contentView = null;
        if (layout instanceof View) {
            contentView = (View) layout;
        } else if (layout instanceof Integer) {
            contentView = getLayoutInflater().inflate((Integer) layout, parent, false);
        }
        if (contentView == null) {
            new IllegalArgumentException("getContentLayout must View or LayoutId");
        }
        return contentView;
    }


    /**
     * 1. 初始化数据，包括上个页面传递过来的数据在这个方法做
     */
    protected void initBundleData(Bundle savedInstanceState) { }

    /**
     * 2.获取布局
     * @return
     */
    protected abstract Object getContentLayout();
    /**
     * 3. 初始化View
     */
    protected abstract void initView(View contentView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 懒加载
     */
    private void loadData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated)) {
            isDataInitiated = true;
            lazyLoad();
        }
    }
    /**
     * 6. 懒加载，Fragment可见的时候调用这个方法，而且只调用一次
     */
    protected void lazyLoad() {

    }
    /**
     * 打开Activity
     */
    public final void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 打开Activity
     */
    public final void startActivity(Class<?> clazz,  Bundle options) {
        Intent intent = new Intent(getActivity(), clazz);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
    }

    /**
     * 闭关页面
     */
    public void finish() {
        if (mActivity != null) {
            mActivity.finish();
        }
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
