package com.reborn.skin.http.api;

import com.reborn.skin.http.retrofit.HttpResponse;

import java.util.TreeMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by 戴震宇 on 2018/8/10 0010.
 * Api 接口
 */
public interface Api {
    /**
     * GET 请求
     * @param url   api接口url
     * @param request   请求参数map
     * @return
     */
    @GET
    Observable<HttpResponse> get(@Url String url , @QueryMap TreeMap<String , Object> request);

    /**
     * POST请求
     * @param url   api接口url
     * @param request   请求参数map
     * @return
     */
    @POST
    Observable<HttpResponse> post(@Url String url, @QueryMap TreeMap<String ,Object>request);

}
