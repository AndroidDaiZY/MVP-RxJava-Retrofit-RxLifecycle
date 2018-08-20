package com.reborn.skin.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;
import com.reborn.skin.application.BaseApplication;
import com.reborn.skin.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *  Retrofit  管理类
 */
public class RetrofitManager {
    protected Retrofit mRetrofit;

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60 ;

    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    //默认超时时间
    private static final  int DEFAULT_TIME = 10 ;

    private static RetrofitManager mInstance = null;
    public static RetrofitManager getInstance(){
        if (mInstance ==null){
            synchronized (RetrofitManager.class){
                if (mInstance == null){
                    mInstance=new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    public Retrofit retrofit(){
        mRetrofit = new Retrofit.Builder()
                .client(initOkHttpClient())
                .baseUrl(ApiConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       return mRetrofit;
    }
    private static OkHttpClient initOkHttpClient() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e("okHttp:"+message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //指定缓存路径   缓存大小100M
        Cache cache=new Cache(new File(BaseApplication.getInstance().getCacheDir(),"skin"),1024*1024*100);
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        return mOkHttpClient;
    }


    //云端响应头拦截器  用来配置缓存策略
    //
    private  static Interceptor mRewriteCacheControlInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //对request的设置用来指定有网/无网下所走的方式
            //对response的设置用来指定有网/无网下的缓存时长
            Request request = chain.request();
            if (!NetUtils.isNetWorkConnected()) {
                //无网络下强制使用缓存,无论缓存是否过期,此时该请求实际上不会被发送出去。
                //有网络时则根据缓存时长来决定是否发出请求
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            //有网络情况下,超过1分钟,则重新请求,否则直接使用缓存数据
            if (NetUtils.isNetWorkConnected()) {
                //有网的时候读接口上的@Headers 里的配置   这里进行统一设置
                String cacheControl = "public,max-age=" + CACHE_STALE_SHORT;//缓存一分钟
                //当然如果你想在有网络的情况下都直接走网络,那么只需要
                // 将其超时时间maxAge设为0即可
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {//无网络时直接取缓存数据,该缓存数据保存1周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public , only-if-cached,max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };
}
