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
     * @return 如果匹配上了，返回target首字符在source中的下标，如果没匹配上，返回-1
     */
    int matches(String source,String target);
}
