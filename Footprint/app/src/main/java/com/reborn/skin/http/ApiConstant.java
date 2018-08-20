package com.reborn.skin.http;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 */
public class ApiConstant {
    //后台返回code
    public static final int REQUEST_SUCCESS = 0; //请求成功
    public static final int REQUEST_WAY_ERROR = 20001;//请求方式不对
    public static final int TOKEN_INVALID = 10001;//token 无效了，重新登录
    public static final int NO_USER_LOGIN=10000;//用户没有登录
    public static final int NO_PROJECT=1001;//没有这个项目

    //服务器内网 测试地址
    public static final String BASE_URL="http://192.168.1.123";
}
