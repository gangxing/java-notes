package com.learn.webdemo.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/7 15:44
 */
@Slf4j
public class BeanUtils {

    public static void merge(Object source, Object target, boolean copyNull) {
        if (source.getClass() != target.getClass()) {
            throw new IllegalArgumentException("target must is the same class with source");
        }
        BeanInfo beanInfo = new BeanInfo(source.getClass());
        Map<Field, Method> fieldGetterMap = beanInfo.getFieldGetterMap();
        Map<Field, Method> fieldSetterMap = beanInfo.getFieldSetterMap();

        Set<Map.Entry<Field, Method>> entrySet = fieldGetterMap.entrySet();

        for (Map.Entry<Field, Method> entry : entrySet) {
            Field field = entry.getKey();
            Method getter = entry.getValue();
            try {
                Object value = getter.invoke(source);
                if (!copyNull && null == value) {
                    continue;
                }
                Method setter = fieldSetterMap.get(field);
                setter.invoke(target, value);

            } catch (Exception e) {
                log.error( "[convertBean失败]" + e.toString(), e);
            }

        }

    }
}
