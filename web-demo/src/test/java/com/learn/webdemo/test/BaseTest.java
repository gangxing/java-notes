package com.learn.webdemo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.learn.webdemo.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:04
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
//@ActiveProfiles(profiles = {"dev"})//默认也是dev
public class BaseTest {


  private long start;

  @Before
  public void before() {
    start = System.currentTimeMillis();
  }

  @After
  public void after() {
    long end = System.currentTimeMillis();
    print("耗时：" + (end - start) + " ms");
  }

  protected void print(Object object) {
    System.err.println(JSON.toJSONString(object,
        SerializerFeature.PrettyFormat,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteDateUseDateFormat,
        SerializerFeature.MapSortField));
  }
}
