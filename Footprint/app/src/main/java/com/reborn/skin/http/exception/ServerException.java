package com.reborn.skin.http.exception;

/**
 * Created by 戴震宇 on 2018/6/27 0027.
 * 自定义服务器错误
 */

public class ServerException extends RuntimeException {

    private int errcode;
    private String errmsg;
    public ServerException(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

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
}
