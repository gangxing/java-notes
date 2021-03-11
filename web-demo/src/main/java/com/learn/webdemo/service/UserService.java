package com.learn.webdemo.service;

import com.learn.webdemo.model.entity.UserEntity;
import java.util.Date;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:03
 */
public interface UserService {

  /**
   * 创建用户
   *
   * @param name
   * @param avatar
   * @param birthday
   * @return
   */
  UserEntity create(String name, String avatar, Date birthday);


  /**
   * 查询
   *
   * @param id
   * @return
   */
  UserEntity selectById(Long id);

  /**
   * 增加粉丝/关注数量
   *
   * @param id
   * @param fanCountIncr
   * @param followCountIncr
   */

  void incrCount(Long id, Integer fanCountIncr, Integer followCountIncr);

}
