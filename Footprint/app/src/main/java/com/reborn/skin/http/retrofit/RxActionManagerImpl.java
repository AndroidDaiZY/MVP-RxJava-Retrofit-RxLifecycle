package com.reborn.skin.http.retrofit;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 */
public class RxActionManagerImpl implements RxActionManager<Object> {
    private static volatile RxActionManagerImpl mInstance;

    private ArrayMap<Object,Disposable>mMaps;//处理，请求列表

    public static RxActionManagerImpl getInstance() {
        if (mInstance == null){
            synchronized (RxActionManagerImpl.class){
                if (mInstance == null){
                    mInstance = new RxActionManagerImpl();
                }
            }
        }
        return mInstance;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public RxActionManagerImpl() {
        this.mMaps = new ArrayMap<>();
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void add(Object tag, Disposable disposable) {
        mMaps.put(tag,disposable);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void remove(Object tag) {
        if (! mMaps.isEmpty()){
            mMaps.remove(tag);
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void cancel(Object tag) {
        if (mMaps.isEmpty()){
            return;
        }
        if (mMaps.get(tag) == null){
            return;
        }
        if (!mMaps.get(tag).isDisposed()){
            mMaps.get(tag).dispose();
        }
        mMaps.remove(tag);
    }

    public boolean isDisposed(Object tag){
        if (mMaps.isEmpty()||mMaps.get(tag)==null)return true;
        return mMaps.get(tag).isDisposed();
    }
}
