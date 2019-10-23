package com.learn.algorithm.charactersmatch;

import java.util.HashMap;
import java.util.Map;

/**
 * 利用"坏字符"和"好后缀"每次向后跳跃移动匹配，尽可能避免不必要的比对
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 13:50
 */
public class BMMatcher implements Matcher {

    @Override
    public boolean matches(String source, String target) {
        return false;
    }

    public static void main(String[] args) {
        Map<Character,Boolean> map=new HashMap<>();
        map.put(new Character('a'),true);

        System.err.println(new Character('a')==new Character('a'));

        System.err.println(map.get(new Character('a')));

    }
}
