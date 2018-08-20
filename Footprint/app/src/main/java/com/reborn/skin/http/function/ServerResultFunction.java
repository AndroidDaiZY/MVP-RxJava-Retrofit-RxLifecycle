package com.reborn.skin.http.function;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.reborn.skin.http.ApiConstant;
import com.reborn.skin.http.exception.ServerException;
import com.reborn.skin.http.retrofit.HttpResponse;

import io.reactivex.functions.Function;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *   变换 : 将服务器 返回 的原始数据 剥离成 我们最终想要的数据
 *    将BaseResponse<T>  转换为T
 *
 *
 *    code 根据服务器返回
 *    抛出服务器错误 返回数据为 errmsg  根据后台返回为主
 */


public class ServerResultFunction implements Function<HttpResponse,Object> {
    @Override
    public Object apply(HttpResponse httpResponse) throws Exception {
        //打印返回的结果
        Logger.e("HttpResponse:"+httpResponse.toString());
        if (!httpResponse.isSuccess()){
            int code =httpResponse.getErrcode();
            String msg=httpResponse.getErrmsg();
            if (code == ApiConstant.NO_PROJECT) { //没有项目
                msg=new Gson().toJson(httpResponse.getErrmsg());
            } else if (code == ApiConstant.NO_USER_LOGIN) {//没有登录
                msg=new Gson().toJson(httpResponse.getErrmsg());
            }
            else if (code ==ApiConstant.TOKEN_INVALID)//token失效
            {
                msg=new Gson().toJson(httpResponse.getErrmsg());
            }
            else if (code == ApiConstant.REQUEST_WAY_ERROR){//请求方式不对
                msg=new Gson().toJson(httpResponse.getErrmsg());
            }
            throw new ServerException(httpResponse.getErrcode(), msg);//抛出服务器错误
        }
        return new Gson().toJson(httpResponse.getData());
    }
}
