package com.learn.webdemo.controller;

import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.model.response.ApiResponse;
import com.learn.webdemo.model.response.ApiResponses;
import com.learn.webdemo.service.FanService;
import io.micrometer.core.annotation.Timed;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:00
 */
@Slf4j
@RestController
@RequestMapping("/fan")
public class FanController {

  @Autowired
  private FanService fanService;

  @Timed
  @GetMapping(value = "/test")
  public ApiResponse<String> test(){
    long time= 500;
//    long time= RandomUtils.nextLong(10,500);
    try {
      TimeUnit.MILLISECONDS.sleep(time);
    }catch (InterruptedException e){}
    System.err.println("test sleep "+time+" ms");
    return ApiResponses.success("sleep"+time);
  }


//  @RequestMapping(value = "/follow", method = RequestMethod.POST)
//  public ApiResponse follow(@RequestBody FollowRequest request) {
//    fxanService.follow(request);
////        log.info(myFanService.getClass().getName());
//    log.info(fxanService.getClass().getName());
////        log.info("equals "+myFanService.getClass().equals(fanService.getClass()));
//    return ApiResponses.success();
//  }
//
//  @RequestMapping(value = "/defollow", method = RequestMethod.POST)
//  public ApiResponse defollow(@RequestBody FollowRequest request) {
//    fxanService.defollow(request);
//    return ApiResponses.success();
//  }
}
