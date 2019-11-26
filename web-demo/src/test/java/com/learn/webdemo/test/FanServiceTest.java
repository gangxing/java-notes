package com.learn.webdemo.test;

import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.service.FanService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:48
 */
public class FanServiceTest extends BaseTest {

    @Autowired
    private FanService fanService;

    @Test
    public void testFollow(){
        FollowRequest request=new FollowRequest();
        request.setUserId(1000000L);
        request.setFanUserId(1000001L);
        fanService.follow(request);
    }
}
