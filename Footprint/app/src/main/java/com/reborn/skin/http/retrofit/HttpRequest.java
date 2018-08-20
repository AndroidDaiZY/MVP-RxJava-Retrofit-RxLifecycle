package com.reborn.skin.http.retrofit;

import com.reborn.skin.http.RetrofitManager;
import com.reborn.skin.http.api.Api;
import com.reborn.skin.http.observer.HttpRxCallback;
import com.reborn.skin.http.observer.HttpRxObservable;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.TreeMap;

import io.reactivex.Observable;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *  Http 请求类
 *   进一步封装 请求参数  和 Api 接口 类
 *   构造函数的不同管理不同界面对应 Activity/Fragment 生命周期的管理
 */
public class HttpRequest {
    public static final String API_URL = "API_URL";

    private final String appKey = "";

    private final String k_key = "";

    public enum Method{
        GET,
        POST
    }
    /**
     * 发送请求
     * 备注:不管理生命周期
     *
     * @param method   请求方式
     * @param params    参数集合
     * @param callback 回调
     */
    public void request(Method method , TreeMap<String,Object>params, HttpRxCallback callback){
        Observable<HttpResponse> apiObservable =handleRequest(method,params);
        HttpRxObservable.getObservable(apiObservable,callback).subscribe(callback);
    }
    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider lifecycle, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback);
    }
    /**
     * 发送请求
     * 备注:手动指定生命周期-Activity
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }
    /**
     * 发送请求
     * 备注:手动指定生命周期-Fragment
     *
     * @param method    请求方式
     * @param lifecycle 实现RxFragment
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }

    /**
     * 预处理请求
     *
     * @param method 请求方法
     * @param prams  参数集合
     * @return
     */
    private Observable<HttpResponse> handleRequest(Method method, TreeMap<String, Object> prams) {

        //获取基础参数
        TreeMap<String, Object> request = getBaseRequest();
        //添加业务参数
        request.putAll(prams);
        //获取apiUrl
        String apiUrl = "";
        if (request.containsKey(API_URL)) {
            apiUrl = String.valueOf(request.get(API_URL));
            //移除apiUrl参数（此参数不纳入业务参数）
            request.remove(API_URL);
        }

        Observable<HttpResponse> apiObservable;
        switch (method) {
            case GET:
                apiObservable = RetrofitManager.getInstance().retrofit().create(Api.class).get(apiUrl,request);
                break;
            case POST:
                apiObservable = RetrofitManager.getInstance().retrofit().create(Api.class).post(apiUrl, request);
                break;
            default:
                apiObservable = RetrofitManager.getInstance().retrofit().create(Api.class).post(apiUrl, request);
                break;
        }
        return apiObservable;
    }

    /**
     * 获取基础request参数
     */
    private TreeMap<String, Object> getBaseRequest() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(k_key, appKey);
        return map;
    }
}
