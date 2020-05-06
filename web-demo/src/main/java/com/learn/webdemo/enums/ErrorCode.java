package com.learn.webdemo.enums;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:07
 */
public enum ErrorCode {

  SUCCESS(200),


  //bad request
  PARAM_INVALID(400),
  NOT_LOGIN(401),
  //Has no permission
  FORBIDDEN(403),
  METHOD_NOT_FOUND(404),
  METHOD_NOT_ALLOWED(405),
  INTERNAL_ERROR(500),
  RESULT_IS_NULL(523),
  SYSTEM_BUSY(524),
  HTTP_ERROR(525);

  private Integer code;


  ErrorCode(Integer code) {
    this.code = code;
  }

  public static ErrorCode findByCode(Integer code) {
    for (ErrorCode item : values()) {
      if (item.getCode().equals(code)) {
        return item;
      }
    }
    return null;
  }

  public Integer getCode() {
    return code;
  }


}
