package com.reborn.skin.mvp;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 * View层 接口层
 */
public interface IBaseView {
    /**
     * 界面加载时  加载框 弹出框 提示框 动画等效果
     */
    void showLoading();

    /**
     *  界面请求结束后 关闭 弹出框 动画等效果
     */
    void closeLoading();

    /**
     *  请求后的一些提示
     * @param msg  后台返回的信息
     */
    void showToast(String msg);
}
