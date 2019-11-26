package com.learn.webdemo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.webdemo.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:06
 */
@Getter
@Setter
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private String detail;

    private T data;

    @JSONField(serialize = false)
    public boolean succeed() {
        return ErrorCode.SUCCESS.getCode().equals(code);
    }

}
