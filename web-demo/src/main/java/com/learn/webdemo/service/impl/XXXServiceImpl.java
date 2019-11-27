package com.learn.webdemo.service.impl;

import com.learn.webdemo.enums.FlagEnum;
import com.learn.webdemo.mapper.FanMapper;
import com.learn.webdemo.model.entity.FanEntity;
import com.learn.webdemo.model.query.FanQuery;
import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.service.FanService;
import com.learn.webdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:04
 */
@Service
public class XXXServiceImpl implements FanService {

    @Autowired
    private UserService userService;

    @Autowired
    private FanMapper fanMapper;


    @Transactional
    @Override
    public void follow(FollowRequest request) {
        FanEntity fan = fanMapper.selectOne(build(request));
        boolean follow = true;
        if (fan == null) {
            fan = buildEntity(request);
            fanMapper.insert(fan);
        } else if (FlagEnum.yes(fan.getDelFlag())) {
            FanEntity update = new FanEntity();
            update.setId(fan.getId());
            update.setDelFlag(FlagEnum.no());
            update.setUpdatedAt(new Date());
            fanMapper.updateById(update);
        } else {
            follow = false;
        }

        if (follow) {
            //增加关注数
            userService.incrCount(request.getFanUserId(), null, 1);

            //增加粉丝数
            userService.incrCount(request.getUserId(), 1, null);
        }

    }

    @Transactional
    @Override
    public void defollow(FollowRequest request) {

        FanEntity fan = fanMapper.selectOne(build(request));
        if (fan == null || FlagEnum.yes(fan.getDelFlag())) {
            return;
        }

        FanEntity update = new FanEntity();
        update.setId(fan.getId());
        update.setDelFlag(FlagEnum.yes());
        update.setUpdatedAt(new Date());
        fanMapper.updateById(update);


        //减少关注数
        userService.incrCount(request.getFanUserId(), null, -1);

        //减少粉丝数
        userService.incrCount(request.getUserId(), -1, null);
    }

    private FanQuery build(FollowRequest request) {
        FanQuery query = new FanQuery();
        query.setUserId(request.getUserId());
        query.setFanUserId(request.getFanUserId());
        return query;
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
