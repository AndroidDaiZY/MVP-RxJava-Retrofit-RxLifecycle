package com.reborn.skin.http.helper;

import com.google.gson.JsonElement;

/**
 * Created by 戴震宇 on 2018/6/27 0027.
 * 数据解析helper
 */

public interface ParseHelper<T> {
    Object[] parse(JsonElement jsonElement);
}
