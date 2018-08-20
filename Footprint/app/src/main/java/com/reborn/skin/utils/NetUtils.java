package com.reborn.skin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.reborn.skin.application.BaseApplication;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 */
public class NetUtils {
    private NetUtils(){

    }
    //是否有网络连接
    public static boolean isNetWorkConnected(){
        if (BaseApplication.getInstance()!=null){
            ConnectivityManager mConnectivityManager= (ConnectivityManager) BaseApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission")
            NetworkInfo mNetworkInfo= mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //是否有wifi 连接
    public static boolean isWifiConnected(){
        if (BaseApplication.getInstance()!=null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo !=null){
                return mWiFiNetworkInfo.isAvailable();
            }
        }

        return false;
    }
    //手机是否连接
    public static boolean isMobileConnected() {
        if (BaseApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (BaseApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
