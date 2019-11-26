package com.learn.webdemo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description TODO
 * @Author xgangzai
 * @Date 07/07/2018 22:26
 */
public class BeanInfo {

    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String GET_CLASS = "getClass";

//    private List<Field> declaredFields=Lists.newArrayList();

    //保证放入的顺序
    private Map<Field, Method> fieldGetterMap = new LinkedHashMap<>();

    //保证放入的顺序
    private Map<Field, Method> fieldSetterMap = new LinkedHashMap<>();

    //保证放入的顺序
    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    public BeanInfo(Class cla) {
        List<Method> methodList = new ArrayList<>();
        for (Class<?> clazz = cla; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Method[] methods = clazz.getDeclaredMethods();
                Collections.addAll(methodList, methods);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!SERIAL_VERSION_UID.equals(field.getName())) {
                        fieldMap.put(field.getName(), field);
//                        declaredFields.add(field);
                    }
                }
            } catch (Exception e) {
                //no op
            }
        }

        for (Method method : methodList) {
            String methodName = method.getName();
            if (methodName.startsWith(GET) && !GET_CLASS.equals(methodName)) {
                String fieldName = methodName.replace(GET, "");
                Field field = getField(fieldName, fieldMap);
                if (field != null) {
                    fieldGetterMap.put(field, method);
                }
                continue;

            }

            if (methodName.startsWith(SET)) {
                String fieldName = methodName.replace(SET, "");
                Field field = getField(fieldName, fieldMap);
                if (field != null) {
                    fieldSetterMap.put(field, method);
                }

            }
        }
    }

    private static Field getField(String fieldName, Map<String, Field> fieldMap) {
        Set<Map.Entry<String, Field>> entries = fieldMap.entrySet();
        for (Map.Entry<String, Field> entry : entries) {
            if (entry.getKey().equalsIgnoreCase(fieldName)) {
                return entry.getValue();
            }
        }
        return null;
    }


    public List<Field> getDeclaredFields() {
        List<Field> fields = new ArrayList<>();
        fieldMap.forEach((k, v) -> fields.add(v));
        return fields;
    }

    public Map<String, Field> getFieldMap() {
        return fieldMap;

    }

    public List<Method> getGetters() {
        List<Method> list = new ArrayList<>();
        Collections.addAll(fieldGetterMap.values());
        return list;
    }

    public List<Method> getSetters() {
        List<Method> list = new ArrayList<>();
        Collections.addAll(fieldSetterMap.values());
        return list;
    }


    public Map<Field, Method> getFieldGetterMap() {
        return fieldGetterMap;
    }

    public Map<Field, Method> getFieldSetterMap() {
        return fieldSetterMap;
    }
}
