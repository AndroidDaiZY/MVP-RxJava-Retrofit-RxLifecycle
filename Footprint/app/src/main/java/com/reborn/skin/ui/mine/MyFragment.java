package com.reborn.skin.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.reborn.skin.R;
import com.reborn.skin.ui.base.BaseFragment;

/**
 * Created by 戴震宇 on 2018/8/13 0013.
 */
public class MyFragment extends BaseFragment {
    @Override
    protected Object getContentLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected void initData() {

    }
    //解决fragment重叠现象
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);//将这一行注释掉，阻止activity保存fragment的状态
    }
}
