package com.learn.algorithm.charactersmatch;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 10:10
 * @see <href>https://blog.csdn.net/v_july_v/article/details/7041827</href>
 */
public interface Matcher {

    /**
     * source中是否包含target子串
     * @param source
     * @param target
     * @return
     */
    boolean matches(String source,String target);
}
