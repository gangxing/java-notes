package com.learn.webdemo.service.impl;

import com.learn.webdemo.mapper.UserMapper;
import com.learn.webdemo.model.entity.UserEntity;
import com.learn.webdemo.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:03
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PlatformTransactionManager platformTransactionManager;

  @Override
  public UserEntity create(String name, String avatar, Date birthday) {
    UserEntity user = new UserEntity();
    user.setAvatar(avatar);
    user.setBirthday(birthday);
    user.setName(name);
    user.setFanCount(0);
    user.setFollowCount(0);
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    userMapper.insert(user);
    return user;
  }

  @Override
  public UserEntity selectById(Long id) {
    return userMapper.selectById(id);
  }

  @Transactional(propagation = Propagation.REQUIRED,timeout = 5)
  @Override
  public void incrCount(Long id, Integer fanCountIncr, Integer followCountIncr) {
    userMapper.incrCount(id, fanCountIncr, followCountIncr);
//    if (id != null) {
//      throw new RuntimeException("第二个内部事务抛错");
//    }


  }


}
