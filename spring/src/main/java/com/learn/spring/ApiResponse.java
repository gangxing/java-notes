package com.learn.spring;

import java.io.Serializable;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/29 16:06
 */
public class ApiResponse<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private String detail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
