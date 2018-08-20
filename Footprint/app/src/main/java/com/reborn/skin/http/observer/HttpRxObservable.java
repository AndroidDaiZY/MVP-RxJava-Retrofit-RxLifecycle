package com.reborn.skin.http.observer;

import com.reborn.skin.http.function.HttpResultFunction;
import com.reborn.skin.http.function.ServerResultFunction;
import com.reborn.skin.http.retrofit.HttpResponse;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *  适用Retrofit 网络请求Observable(被监听者)
 *
 *      对Activity fragment FragmentActivity DialogFragment
 */
public class HttpRxObservable {
    /**
     * 获取被监听者
     * 注: 网络请求Observable 构建
     * data: 网络请求参数
     *
     *  无管理生命周期 容易导致内存溢出
     */

    public static Observable getObservable(Observable<HttpResponse>apiObservable , final HttpRxCallback callback){
        Observable observable = apiObservable
                .map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (callback != null){
                            callback.onCanceled();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    /**
     * 获取被监听者
     * 注: 网络请求Observable 构建
     * data: 网络请求参数
     *
     * 传入 LifecycleProvider 自动管理生命周期  避免内存溢出
     * 备注: 需要继承RxActivity../RxFragment ..
     */
    public static Observable getObservable(Observable<HttpResponse>apiObservable, LifecycleProvider lifecycle, final HttpRxCallback callback){
        Observable observable;
        if (lifecycle != null){
            //随生命周期自动管理 onCreate(start)->onStop(end)
            observable=apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindToLifecycle())//此处添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .doOnDispose(new Action() {
                        @Override
                        public void run() throws Exception {
                            if (callback != null){
                                callback.onCanceled();
                            }
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }else {
            observable = getObservable(apiObservable,callback);
        }
        return observable;
    }
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     *
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event, final HttpRxCallback callback) {
        // showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:ActivityEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .doOnDispose(new Action() {
                        @Override
                        public void run() throws Exception {
                            if (callback != null)
                                callback.onCanceled();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable, callback);
        }
        return observable;
    }
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     */
    public static Observable getObservable(Observable<HttpResponse> apiObservable, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event, final HttpRxCallback callback) {
        //  showLog(request);
        Observable observable;
        if (lifecycle != null) {
            //手动管理移除监听生命周期.eg:FragmentEvent.STOP
            observable = apiObservable
                    .map(new ServerResultFunction())
                    .compose(lifecycle.bindUntilEvent(event))//需要在这个位置添加
                    .onErrorResumeNext(new HttpResultFunction<>())
                    .doOnDispose(new Action() {
                        @Override
                        public void run() throws Exception {
                            if (callback != null)
                                callback.onCanceled();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable, callback);
        }
        return observable;
    }
}
