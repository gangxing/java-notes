package com.learn.webdemo.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:01
 */
@Getter
@Setter
public class FollowRequest extends BaseRequest {

  private Long userId;

  private Long fanUserId;
}
