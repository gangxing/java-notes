package com.learn.spring;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/29 16:04
 */
public class GsonTest {

    public static void main(String[] args) throws Exception{
        String s="{\"code\":200,\"msg\":null,\"detail\":null,\"data\":{\"name\":\"张三\",\"age\":19}}";
//        Gson gson=new Gson();
//
//        TypeToken<ApiResponse<Student>> typeToken=new TypeToken<ApiResponse<Student>>(){};
//        ApiResponse<Student> apiResponse=gson.fromJson(s,typeToken.getType());
        Student student=parse(s,Student.class);
        System.err.println(student.getName());
    }



    private static <T>T parse(String s,Class<T> tClass){
        Gson gson=new Gson();
        Type type=new ParameterizedTypeImpl(ApiResponse.class,tClass)

        TypeToken<ApiResponse<T>> typeToken=new TypeToken<ApiResponse<T>>(){};
        ApiResponse<T> apiResponse=gson.fromJson(s,typeToken.getType());
        return apiResponse.getData();
    }

}
