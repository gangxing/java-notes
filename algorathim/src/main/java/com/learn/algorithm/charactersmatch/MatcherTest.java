package com.learn.algorithm.charactersmatch;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 09:59
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串匹配
 */
@Slf4j
public class MatcherTest {


    public static void main(String[] args) {
//        Matcher matcher=new BFMatcher();
//        Matcher matcher=new RKMatcher();
        Matcher matcher=new BMMatcher();
        String source="ADBCjdjj39922j3jdd";
        String target="jj39";
        log.info(""+matcher.matches(source,target));
    }
}
