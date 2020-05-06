package com.learn.webdemo.model.response;


import com.learn.webdemo.enums.ErrorCode;

/**
 * @Description 接口响应参数工具类
 * @Author xgangzai
 * @Date 02/07/2018 00:10
 */
public class ApiResponses {


  public static <T> ApiResponse<T> success(T data) {
    ApiResponse<T> apiResponse = new ApiResponse<T>();
    apiResponse.setCode(ErrorCode.SUCCESS.getCode());
    apiResponse.setData(data);
    return apiResponse;
  }

  public static <T> ApiResponse<T> success() {
    return success(null);
  }

  public static ApiResponse fail(ErrorCode code, String detail) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(code.getCode());
    apiResponse.setDetail(detail);
    //错误消息统一在拦截器中填充
    apiResponse.setMsg(code.name());
    return apiResponse;
  }

  public static ApiResponse fail(ErrorCode code) {
    return fail(code, code.name());
  }


}
