package com.reborn.skin.http.retrofit;

/**
 * Created by 戴震宇 on 2018/6/27 0027.
 * 请求监听接口
 */

public interface HttpRequestListener {
    /**
     *  取消请求
     */
    void cancel();
    /**
     * 请求被取消
     */
    void onCanceled();
}
