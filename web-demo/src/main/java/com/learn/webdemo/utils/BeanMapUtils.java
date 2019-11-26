package com.learn.webdemo.utils;

import com.learn.webdemo.annotation.MapKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description TODO
 * @Author XingGang
 * @Date 08/04/2017 22:15
 */

/**
 * @see:http://4805119.blog.51cto.com/4795119/1614320
 */

@Slf4j
public class BeanMapUtils {

    // map to bean
    @SuppressWarnings("unchecked")
    public static <T> T convertMap(Map map, Class<T> beanClass) {

        try {
            T t = beanClass.newInstance();
            BeanInfo beanInfo = new BeanInfo(beanClass);
            Map<Field, Method> fieldMethodMap = beanInfo.getFieldSetterMap();
            for (Map.Entry<Field, Method> entry : fieldMethodMap.entrySet()) {
                Field field = entry.getKey();
                String key = entry.getKey().getName();

                if (!map.containsKey(key)) {
                    MapKey mapKey = field.getAnnotation(MapKey.class);
                    if (mapKey != null) {
                        key = mapKey.name();
                    }
                }
                if (map.containsKey(key) && map.get(key) != null) {
                    Method setter = entry.getValue();
                    setter.invoke(t, map.get(key));
                    map.remove(key);
                }

            }

            return t;
        } catch (Exception e) {
            log.error("[convertMap失败]" + e.toString(), e);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertBean(Object object, boolean removeNull) {
        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo = new BeanInfo(object.getClass());
        Map<Field, Method> fieldMethodMap = beanInfo.getFieldGetterMap();
        Set<Map.Entry<Field, Method>> entrySet = fieldMethodMap.entrySet();

        for (Map.Entry<Field, Method> entry : entrySet) {
            Field field = entry.getKey();
            String key = field.getName();
            Method getter = entry.getValue();
            MapKey mapKey = field.getAnnotation(MapKey.class);
            if (mapKey != null && StringUtils.isNotEmpty(mapKey.name())) {
                key = mapKey.name();
            }
            try {
                Object value = getter.invoke(object);
                if (removeNull && null == value) {
                    continue;
                }
                map.put(key, value);

            } catch (Exception e) {
                log.error("[convertBean失败]" + e.toString(), e);
            }

        }

        return map;
    }


    /**
     * 将Bean转成 Map<String, String>
     *
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> convert2StringMap(Object object, boolean removeNull) {
        Map<String, Object> map = convertBean(object, removeNull);
        return convertValueType(map);
    }

    /**
     * 将Map<String, Object> 转成 Map<String, String>
     *
     * @param objectMap
     * @return
     */
    private static Map<String, String> convertValueType(Map<String, Object> objectMap) {
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            result.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return result;
    }

}
