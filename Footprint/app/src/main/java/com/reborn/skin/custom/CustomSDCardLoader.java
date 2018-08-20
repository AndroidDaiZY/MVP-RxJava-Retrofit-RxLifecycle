package com.reborn.skin.custom;

import android.content.Context;

import java.io.File;

import skin.support.load.SkinSDCardLoader;

/**
 * Created by 戴震宇 on 2018/8/13 0013.
 */
public class CustomSDCardLoader extends SkinSDCardLoader {
    public static final int SKIN_LOADER_STRATEGY_SDCARD = Integer.MAX_VALUE;

    @Override
    public String loadSkinInBackground(Context context, String skinName) {
        return super.loadSkinInBackground(context, skinName);
    }
    //自定义加载皮肤包的路径（我们的皮肤包必须要放在getSkinPath返回的路径下。否则加载失败）
    @Override
    protected String getSkinPath(Context context, String skinName) {
        return new File("sdcard"+File.separator+skinName).getAbsolutePath();
    }

    @Override
    public int getType() {
        return SKIN_LOADER_STRATEGY_SDCARD;
    }

}
