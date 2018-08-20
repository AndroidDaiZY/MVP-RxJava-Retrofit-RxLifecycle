package com.reborn.skin.http.exception;

import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 */
public class ExceptionEngine {

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 1002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 1003;//网络连接错误
    public static final int TIME_OUT_ERROR = 1004;//网络连接超时


    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){
            HttpException httpExc = (HttpException) e;
            ex =new ApiException(e,httpExc.code());
            ex.setMsg("网络错误");//均视为网络错误
            return  ex;
        }else if (e instanceof  ServerException){ //服务器返回错误
            ServerException serverExc= (ServerException) e;
            ex = new ApiException(serverExc,serverExc.getErrcode());
            ex.setMsg(serverExc.getErrmsg());
            return  ex;
        }else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof MalformedJsonException){ //解析数据错误
           ex =new ApiException(e,ANALYTIC_SERVER_DATA_ERROR);
           ex.setMsg("解析错误");
           return  ex;
        }else if (e instanceof ConnectException
                || e instanceof SSLHandshakeException
                || e instanceof UnknownHostException){
            ex =new ApiException(e ,CONNECT_ERROR);//连击网络错误
            ex.setMsg("连接失败");
            return  ex;
        }else if (e instanceof SocketTimeoutException){//网络超时
            ex=new ApiException(e,TIME_OUT_ERROR);
            ex.setMsg("连接超时");
            return ex;
        }else {//未知错误
            ex = new ApiException(e,UN_KNOWN_ERROR);
            ex.setMsg("未知错误");
            return ex;
        }
    }

}
