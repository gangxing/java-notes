package com.learn.webdemo.controller;

import com.learn.webdemo.model.request.FollowRequest;
import com.learn.webdemo.model.response.ApiResponse;
import com.learn.webdemo.model.response.ApiResponses;
import com.learn.webdemo.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:00
 */
@RestController
@RequestMapping("/fan")
public class FanController {

    @Autowired
    private FanService fanService;

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ApiResponse follow(@RequestBody FollowRequest request) {
        fanService.follow(request);
        return ApiResponses.success();
    }

    @RequestMapping(value = "/defollow", method = RequestMethod.POST)
    public ApiResponse defollow(@RequestBody FollowRequest request) {
        fanService.defollow(request);
        return ApiResponses.success();
    }
}
