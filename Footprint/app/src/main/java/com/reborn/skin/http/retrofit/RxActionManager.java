package com.reborn.skin.http.retrofit;

import io.reactivex.disposables.Disposable;

/**
 * Created by 戴震宇 on 2018/6/27 0027.
 * RxJavaAction  管理接口
 */

public interface RxActionManager<T> {
    /**
     * 添加
     * @param tag
     * @param disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * 移除
     * @param tag
     */
    void remove(T tag);

    /**
     * 取消
     * @param tag
     */
    void cancel(T tag);
}
