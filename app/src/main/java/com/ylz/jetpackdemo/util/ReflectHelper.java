package com.ylz.jetpackdemo.util;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Author: ChenGuiPing
 * Date: 2021/10/14
 * Description:
 */
public final class ReflectHelper<T> {

    public static final String TAG = "ReflectHelper";
    private static final Object NOT_FOUND = new Object();
    private static final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    private Object instance;
    private final Class<T> clazz;

    private ReflectHelper(Class<T> clazz) {
        this.clazz = clazz;
    }

    private ReflectHelper<T> setInstance(Object o) {
        this.instance = o;
        return this;
    }

    public static ReflectHelper<?> of(Class<?> clazz) {
        return new ReflectHelper<>(clazz);
    }

    public static ReflectHelper<?> of(String className) throws ClassNotFoundException {
        return new ReflectHelper<>(getClassForName(className));
    }

    public static ReflectHelper<?> of(Object instance) {
        return new ReflectHelper<>(instance != null ? instance.getClass() : null).setInstance(instance);
    }

    public Object getField(String fieldName) throws NoSuchFieldException {
        Field field = getClassField(this.clazz, fieldName);
        if (field != null) {
            try {
                return field.get(this.instance);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "getField error:" + e);
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Field getClassField(Class<?> target, String fieldName) throws NoSuchFieldException {
        if (target == null || target == Object.class) {
            return null;
        }
        String key = target.getName() + '#' + fieldName;
        Object value = cacheMap.get(key);
        if (value == NOT_FOUND) {
            Log.d(TAG, "getClassField Not Found:" + key);
            return null;
        }
        Field field = (Field) value;
        if (field == null) {
            field = target.getDeclaredField(fieldName);
            field.setAccessible(true);
//            if (field == null){
//                //递归查找父类
//                field = getClassField(target.getSuperclass(), fieldName);
//            }
            putCache(key, field);
        }
        return field;
    }

    private static Method getClassMethod(Class<?> target, String methodName,
                                         Class<?>[] methodParamTypes) throws NoSuchMethodException {
        if (target == null || target == Object.class) {
            return null;
        }
        String key = target.getName() + '#' + methodName + '(' + Arrays.toString(methodParamTypes) + ')';
        Object value = cacheMap.get(key);
        if (value == NOT_FOUND) {
            Log.d(TAG, "getClassMethod Not Found:" + key);
            return null;
        }
        Method method = target.getDeclaredMethod(methodName, methodParamTypes);
        method.setAccessible(true);
        putCache(key, method);
        return method;
    }

    private static Constructor<?> getClassConstructor(Class<?> target, Class<?>[] paramTypes) throws NoSuchMethodException {
        if (target == null) {
            return null;
        }
        String key = target.getName() + '#' + "<init>" + '(' + Arrays.toString(paramTypes) + ')';
        Object value = cacheMap.get(key);
        if (value == NOT_FOUND) {
            Log.d(TAG, "getClass Not Found:" + key);
            return null;
        }
        Constructor<?> constructor = (Constructor<?>) value;
        if (constructor == null) {
            constructor = target.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            putCache(key, constructor);
        }
        return constructor;
    }

    private static Class<?> getClassForName(String className) throws ClassNotFoundException {
        Object value = cacheMap.get(className);
        if (value == NOT_FOUND) {
            Log.d(TAG, "getClass Not Found:" + className);
            return null;
        }
        Class<?> clazz = (Class<?>) value;
        if (value == null) {
            clazz = Class.forName(className);
            putCache(className, clazz);
        }
        return clazz;
    }

    private static void putCache(String key, Object value) {
        if (value == null || value == NOT_FOUND) {
            Log.d(TAG, "Class is Object!");
            value = NOT_FOUND;
        }
        cacheMap.put(key, value);
    }

}
