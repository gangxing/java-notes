package com.learn.webdemo.service;

import com.learn.webdemo.model.request.FollowRequest;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:02
 */
public interface FanService {


  /**
   * 关注
   *
   * @param request
   */
  void follow(FollowRequest request);

  /**
   * 取消关注
   *
   * @param request
   */
  void defollow(FollowRequest request);
}
