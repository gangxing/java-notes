package com.learn.webdemo.test;

import com.learn.webdemo.mapper.FanMapper;
import com.learn.webdemo.mapper.UserMapper;
import com.learn.webdemo.model.entity.FanEntity;
import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.service.FanService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.util.Assert;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:48
 */
public class FanServiceTest extends BaseTest {

  @Autowired
  private FanService fanService;

  @Autowired
  private FanMapper fanMapper;

  @Autowired
  private UserMapper userMapper;

  static Long userId = 1L;
  static Long fanUserId = 2L;

  static String outerMsg = "外部事务不符合预期";
  static String innerMsg = "内部事务不符合预期";

  @Test
  public void testFollow() {
    try {
      fanService.follow(request(),
          TransactionDefinition.PROPAGATION_REQUIRES_NEW,
          false,
          false);
    } finally {
      assertResult();
      clean();
    }

  }

  @Test
  public void testFollow1() {
    try {
      fanService.follow(request(),
          TransactionDefinition.PROPAGATION_REQUIRES_NEW,
          true,
          false);
    } finally {
//      assertResult();

//      clean();
    }
  }

  @Test
  public void testFollow2() {
    try {
      fanService.follow(request(),
          TransactionDefinition.PROPAGATION_REQUIRES_NEW,
          false,
          true);
    } finally {
//      assertResult();
//
//      clean();
    }
  }


  @Test
  public void testFollow3() {
    try {
      fanService.follow(request(),
          TransactionDefinition.PROPAGATION_NESTED,
          false,
          true);
    } finally {
//      assertResult();
//
//      clean();
    }
  }

  @Test
  public void testFollow4() {
    try {
      fanService.follow(request(),
          TransactionDefinition.PROPAGATION_NOT_SUPPORTED,
          true,
          false);
    } finally {
      assertResult();

      clean();
    }
  }


  private void assertResult() {
    FanEntity fan = fanMapper.selectByUserIdAndFanUserId(userId, fanUserId);
    Assert.isTrue(fan == null, outerMsg);

    Integer fanCount = userMapper.selectById(userId).getFanCount();
    Assert.isTrue(fanCount != 0, innerMsg);
  }


  private void clean() {
    fanMapper.delete(userId, fanUserId);
    userMapper.cleanCount(userId);
  }


  private FollowRequest request() {
    FollowRequest request = new FollowRequest();
    request.setUserId(userId);
    request.setFanUserId(fanUserId);
    return request;
  }


}
