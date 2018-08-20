package com.reborn.skin.http.function;


import com.orhanobut.logger.Logger;
import com.reborn.skin.http.exception.ExceptionEngine;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 * http 结果处理函数
 */
public class HttpResultFunction<T> implements Function<Throwable,Observable<T>> {
    @Override
    public Observable<T> apply(Throwable input) throws Exception{
        Logger.e("HttpResultFunction:"+ input);
        return Observable.error(ExceptionEngine.handleException(input));
    }
}
