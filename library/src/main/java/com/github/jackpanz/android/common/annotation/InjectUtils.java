package com.github.jackpanz.android.common.annotation;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by jack on 2020/10/22 17:40
 * 動態獲得 layout 組件
 */
public class InjectUtils {

    public static void inject(PreferenceActivity activity) throws RuntimeException {
        final Class<?> aClass = activity.getClass();
        //获取当前活动中所有声明的属性，包括私有属性
        final Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(InjectPreference.class)){
                InjectPreference injectView = field.getAnnotation(InjectPreference.class);
                final String name = injectView.value(); //获取注解值（找到控件的id）
                final Preference preference = activity.findPreference(name);
                try {
                    field.setAccessible(true); //设置属性值的访问权限
                    field.set(activity, preference); //将查找到的view指定给目标对象object
                }catch (Exception ex){
                    Log.d("InjectUtils",field.getName() + "有問題");
                    throw new RuntimeException();
                }
            }
        }
    }

    public static void inject(Activity activity) throws RuntimeException {
        final Class<?> aClass = activity.getClass();
        //获取当前活动中所有声明的属性，包括私有属性
        final Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(InjectView.class)){
                InjectView injectView = field.getAnnotation(InjectView.class);
                final int viewId = injectView.value(); //获取注解值（找到控件的id）
                final View v = activity.findViewById(viewId); //通过根view查找此id对应的view
                try {
                    field.setAccessible(true); //设置属性值的访问权限
                    field.set(activity, v); //将查找到的view指定给目标对象object
                }catch (Exception ex){
                    Log.d("InjectUtils",field.getName() + "有問題");
                    throw new RuntimeException();
                }
            }
        }
    }

    public static void inject(Object fragment, View view) throws RuntimeException {
        _inject(fragment,fragment.getClass(),view);
        _inject(fragment,fragment.getClass().getSuperclass(),view);
    }

    public static void _inject(Object fragment, Class clazz, View view) throws RuntimeException {
        final Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(InjectView.class)){
                InjectView injectView = field.getAnnotation(InjectView.class);
                final int viewId = injectView.value(); //获取注解值（找到控件的id）
                final View v = view.findViewById(viewId); //通过根view查找此id对应的view
                try {
                    field.setAccessible(true); //设置属性值的访问权限
                    field.set(fragment, v); //将查找到的view指定给目标对象object
                }catch (Exception ex){
                    Log.d("InjectUtils",field.getName() + "有問題");
                    throw new RuntimeException();
                }
            }
        }
    }

}
