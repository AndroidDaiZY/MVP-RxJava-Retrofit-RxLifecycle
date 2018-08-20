package com.reborn.skin.http.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.reborn.skin.http.ApiConstant;

/**
 * Created by 戴震宇 on 2018/8/10 0010.
 *
 *  http 响应参数实体类
 *      通过Gson 解析属性名称 需要与服务器 返回字段对应  或者用注解 @SerializedName
 */

public class HttpResponse {

    @SerializedName("errcode")
    private int errcode;
    @SerializedName("errmsg")
    private String errmsg;
    @SerializedName("data")
    private JsonElement data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    /**
     * 与服务器 协定好 请求成功返回的 状态码 0 为成功
     * @return  true 表示请求成功 false 反之
     */
    public boolean isSuccess(){return  errcode == ApiConstant.REQUEST_SUCCESS ? true:false;}

    @Override
    public String toString() {
        return "HttpResponse{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", data=" + data +
                '}';
    }
}
