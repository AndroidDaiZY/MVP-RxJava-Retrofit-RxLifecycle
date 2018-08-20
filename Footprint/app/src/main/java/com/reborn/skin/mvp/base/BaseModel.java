package com.reborn.skin.mvp.base;

import com.reborn.skin.http.retrofit.HttpRequest;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 * 基础业务类
 */
public class BaseModel {
    protected HttpRequest mHttpRequest;
    public BaseModel(){
        mHttpRequest = new HttpRequest();
    }
    protected HttpRequest getRequest(){
        if (mHttpRequest == null){
            mHttpRequest =new HttpRequest();
        }
        return mHttpRequest;
    }
}
