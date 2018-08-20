package com.reborn.skin.http.observer;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.reborn.skin.http.ApiConstant;
import com.reborn.skin.http.exception.ApiException;
import com.reborn.skin.http.exception.ExceptionEngine;
import com.reborn.skin.http.helper.ParseHelper;
import com.reborn.skin.http.retrofit.HttpRequestListener;
import com.reborn.skin.http.retrofit.RxActionManagerImpl;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *  适用Retrofit网络请求 Observer（监听者）
 *   注:
 *      1.重写onSubscribe,添加请求标识
 *      2.重写onError, 封装错误/异常处理，移除请求
 *      3.重写onNext,移除请求
 *      4.重写cancel,取消请求
 */
public abstract class HttpRxCallback<T> implements Observer<T>,HttpRequestListener {
    private String mTag; //请求标识
    private ParseHelper parseHelper;//数据解析
    public HttpRxCallback() {
        this.mTag =String.valueOf(System.currentTimeMillis());
    }

    public HttpRxCallback(String mTag) {
        this.mTag = mTag;
    }

    @Override
    public void onError(Throwable e) {
        RxActionManagerImpl.getInstance().remove(mTag);
        if (e instanceof ApiException){
            ApiException exception = (ApiException) e;
            int code = exception.getCode();
            String msg= exception.getMsg();
            if (code == ApiConstant.TOKEN_INVALID){//token失效
                //
                onError(code,msg);
            }else {

                onError(code,msg);
            }
        }else {
            onError(ExceptionEngine.UN_KNOWN_ERROR,"未知错误");
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(@NonNull T t) {
        if (!TextUtils.isEmpty(mTag)){
            RxActionManagerImpl.getInstance().remove(mTag);
        }
        try {
            JsonElement jsonElement = new JsonParser().parse((String) t);
            if (parseHelper != null){
                Object[] res = parseHelper.parse(jsonElement);
                onSuccess(res);
            }else {
                onSuccess(jsonElement);
            }
        }catch (JsonSyntaxException jsonException){
            Logger.e("JsonSyntaxException:"+jsonException.getMessage());
            onError(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR,"解析错误");
        }

    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(mTag)){
            RxActionManagerImpl.getInstance().add(mTag,d);
        }
    }

    /**
     * 手动取消请求
     */
    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)){
            RxActionManagerImpl.getInstance().cancel(mTag);
        }
    }

    /**
     * 请求被取消回调
     */
    @Override
    public void onCanceled() {
        onCancel();
    }

    /**
     * 是否已处理
     * @return
     */
    public boolean isDisposed(){
        if (TextUtils.isEmpty(mTag)){
            return true;
        }
        return RxActionManagerImpl.getInstance().isDisposed(mTag);
    }
    /**
     * 设置解析回调
     *
     * @param parseHelper
     */
    public void setParseHelper(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    /**
     * 失败回调
     *
     * @param code
     * @param desc
     */
    public abstract void onError(int code, String desc);

    /**
     * 成功回调
     *
     * @param object
     */
    public abstract void onSuccess(Object... object);
    /**
     * 取消回调
     */
    public abstract void onCancel();
}
