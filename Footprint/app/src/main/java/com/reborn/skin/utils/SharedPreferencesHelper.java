package com.reborn.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dzy on 2018/3/1 0001.
 * 偏好设置  缓存一些简单的 轻量级的数据  int boolean String 等
 *
 */

public class SharedPreferencesHelper {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME="skin_sp";

    public static final String IS_FIRST_RUN="isFirstRun";

    private static SharedPreferencesHelper sharedPreferencesHelper=null;

    private Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 双层锁
     */
    public static SharedPreferencesHelper getInstance(Context context){
        if (sharedPreferencesHelper == null){
            synchronized (SharedPreferencesHelper.class){
                if (sharedPreferencesHelper ==null){
                    sharedPreferencesHelper=new SharedPreferencesHelper();
                    sharedPreferencesHelper.setContext(context);
                    return sharedPreferencesHelper;
                }
            }
        }
        return sharedPreferencesHelper;
    }
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context,String key,Object object){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        }else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        }else  if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        }else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        }else  if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        }else
        {
            editor.putString(key,object.toString());
        }
    }
    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key,Object defaultObject)
    {
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        }else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        }else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        }else  if (defaultObject instanceof  Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        }else if (defaultObject instanceof Long)
        {
            return  sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }
    public static void remove(Context context,String key)
    {
        SharedPreferences sp=getInstance(context).getSP(key);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * apply 优先于commit  apply 异步存入磁盘  而commit 直接写入commit
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }
        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void  apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }


    private SharedPreferences getSP() {
        return mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    private SharedPreferences getSP(String name) {
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
