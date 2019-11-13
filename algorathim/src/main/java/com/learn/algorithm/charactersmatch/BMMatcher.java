package com.learn.algorithm.charactersmatch;

import java.util.HashMap;
import java.util.Map;

/**
 * 在暴力比对过程中，模式串每次只会向后移动一位，提高效率的核心思想则是尽可能地跳步向后移动，省去已经明确不会匹配上的比对
 * 操作，所以关键问题就在于求向后跳的长度。
 * 利用"坏字符"和"好后缀"可以实现每次向后跳跃移动匹配，尽可能避免不必要的比对
 * 两个规则都用上，实际跳步的长度取两者较大值
 *
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 13:50
 */
public class BMMatcher implements Matcher {

    @Override
    public int matches(String source, String target) {
        char[] targetChars = target.toCharArray();
        char[] sourceChars = source.toCharArray();
        int tl = targetChars.length;
        int sl = sourceChars.length;
        Map<Character, Integer> character2IndexMap = new HashMap<>();
        for (int i = 0; i < tl; i++) {
            //覆盖前面的index，保留最大的
            character2IndexMap.put(targetChars[i], i);
        }


        int position = 0;

        while (position <= sl - tl) {
            boolean matched = true;
            //从后往前比
            for (int i = tl - 1; i >= 0; i--) {
                if (sourceChars[position + i] != targetChars[i]) {
                    matched = false;
                    //出现了坏字符 计算右移长度

                    //模式串中与坏字符对其的字符的下标
                    int si = i;

                    //坏字符在模式串中的最小下标，如果不在则为-1
                    Integer xi = character2IndexMap.get(sourceChars[position + i]);
                    if (xi == null) {
                        xi = -1;
                    }
//                    int step=0;
                    position += si - xi;
                    break;
                }
            }

            if (matched) {
                return position;
            }


        }


        return -1;
    }

    public static void main(String[] args) {
        Map<Character, String> map = new HashMap<>();
        map.put(new Character('a'), "hahhaha");


        System.err.println(new Character('a') == new Character('a'));
        System.err.println(new String("a") == new String("a"));
        System.err.println(new Integer(1) == 1);

        System.err.println(map.get(new Character('a')));

    }
}
