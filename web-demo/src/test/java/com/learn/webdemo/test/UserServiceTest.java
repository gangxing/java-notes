package com.learn.webdemo.test;

import com.learn.webdemo.model.entity.UserEntity;
import com.learn.webdemo.service.UserService;
import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:04
 */
public class UserServiceTest extends BaseTest {

  @Autowired
  private UserService userService;

  @Test
  public void testCreate() {

    for (int i = 0; i < 100; i++) {
      UserEntity user = userService.create("Zhangsan" + i, "http://baidu.com" + i, new Date());
      print(user);
    }
  }
}
