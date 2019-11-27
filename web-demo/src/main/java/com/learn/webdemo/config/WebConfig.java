package com.learn.webdemo.config;

import com.learn.webdemo.service.FanService;
import com.learn.webdemo.service.impl.FanServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 14:59
 */
@Configuration
public class WebConfig {

    @Bean
    public FanService myFanService(){
        return new FanServiceImpl();
    }


}
