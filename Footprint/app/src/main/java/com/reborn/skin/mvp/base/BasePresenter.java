package com.reborn.skin.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.reborn.skin.listener.LifeCycleListener;
import com.reborn.skin.ui.base.BaseActivity;
import com.reborn.skin.ui.base.BaseFragment;
import com.reborn.skin.ui.base.BaseFragmentActivity;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 */
public class BasePresenter<V ,T> implements LifeCycleListener {

    protected Reference<V> mViewRef;

    protected V mView;

    protected Reference<T> mActivityRef;

    protected T mActivity;

    public BasePresenter(V view, T activity) {

    }

    /**
     * 设置生命周期监听
     *
     * @author ZhongDaFeng
     */
    private void setListener(T activity) {
        if (getActivity() != null) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
            } else if (activity instanceof BaseFragmentActivity) {
                ((BaseFragmentActivity) getActivity()).setOnLifeCycleListener(this);
            } else if (activity instanceof BaseFragment) {
                ((BaseFragment) activity).setOnLifeCycleListener(this);
            }
        }
    }

    /**
     * 关联
     * @param view
     */
    private void attachView(V view){
        mViewRef = new WeakReference<V>(view);
        mView=mViewRef.get();
    }

    /**
     * 关联
     *
     * @param activity
     */
    private void attachActivity(T activity) {
        mActivityRef = new WeakReference<T>(activity);
        mActivity = mActivityRef.get();
    }

    /**
     * 销毁
     */
    private void detachView() {
        if (isViewAttached()) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
    /**
     * 销毁
     */
    private void detachActivity() {
        if (isActivityAttached()) {
            mActivityRef.clear();
            mActivityRef = null;
        }
    }
    /**
     * 获取
     *
     * @return
     */
    public V getView() {
        if (mViewRef == null) {
            return null;
        }
        return mViewRef.get();
    }
    /**
     * 获取
     *
     * @return
     */
    public T getActivity() {
        if (mActivityRef == null) {
            return null;
        }
        return mActivityRef.get();
    }
    /**
     * 是否已经关联
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 是否已经关联
     *
     * @return
     */
    public boolean isActivityAttached() {
        return mActivityRef != null && mActivityRef.get() != null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        detachView();
        detachActivity();
    }

    @Override
    public void onAttach(Activity activity) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

    }

    @Override
    public void onActivityCreated(Bundle bundle) {

    }

    @Override
    public void onDestroyView() {
        detachView();
        detachActivity();
    }

    @Override
    public void onDetach() {

    }
}
