package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/14 11:19
 */

/**
 * 内容太多，也没找到什么适用的场景
 * TODO 暂时先放着
 */
@Slf4j
public class CompletableFutureLearn {

    public static void main(String[] args) {
        String result=CompletableFuture.supplyAsync(()->"Hello")
        .thenApplyAsync(s->"World").join();
        log.info(result);
    }
}
