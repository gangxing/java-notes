package com.learn.webdemo.service.impl;

import com.learn.webdemo.enums.FlagEnum;
import com.learn.webdemo.mapper.FanMapper;
import com.learn.webdemo.mapper.UserMapper;
import com.learn.webdemo.model.entity.FanEntity;
import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.service.FanService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:04
 */
@Slf4j
@Service
public class FanServiceImpl implements FanService {

  @Autowired
  private FanMapper fanMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PlatformTransactionManager platformTransactionManager;


  @Override
  public void follow(FollowRequest request, int propagation, boolean outerRollback,
      boolean innerRollback) {
    //默认是REQUIRED
    TransactionTemplate outerTemplate = new TransactionTemplate(platformTransactionManager);
    outerTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus status) {
        fanMapper.insert(buildEntity(request));

        doInner(request, propagation, innerRollback);

        if (outerRollback) {
          status.setRollbackOnly();
          System.err.println("outer roll back");
        }

      }
    });

  }

  private void doInner(FollowRequest request, int propagation, boolean rollback) {
    TransactionTemplate innerTemplate = new TransactionTemplate(platformTransactionManager);
    innerTemplate.setPropagationBehavior(propagation);
    innerTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus status) {
//        userMapper.incrCount(request.getFanUserId(), null, 1);
        userMapper.incrCount(request.getUserId(), 1, null);
        if (rollback) {
          status.setRollbackOnly();
          System.err.println("inner roll back");
//          throw new RuntimeException("inner exception");
        }
      }
    });
  }


  private FanEntity buildEntity(FollowRequest request) {
    FanEntity entity = new FanEntity();
    entity.setFanUserId(request.getFanUserId());
    entity.setUserId(request.getUserId());
    entity.setDelFlag(FlagEnum.no());
    entity.setCreatedAt(new Date());
    entity.setUpdatedAt(new Date());
    return entity;
  }

}
