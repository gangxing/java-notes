package com.learn.algorithm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/12/2 18:28
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 输入一个字符串，字符串中包含了全量字符集和已占用字符集，两个字符集用@相连。@前的字符集合为全量字符集，
 *
 * @后的字符集为已占用字符集合。已占用字符集中的字符一定是全量字符集中的字符。字符集中的字符跟字符之间 使用英文逗号分隔。字符集中的字符表示为字符加数字，字符跟数字使用英文冒号分隔，比如a:1，表示1个a字符。
 * 字符只考虑英文字母，区分大小写，数字只考虑正整形，数量不超过100，如果一个字符都没被占用，
 * @标识符仍在，例如a:3,b:5,c:2@ 说明：全量字符集为3个a，5个b，2个c。已占用字符集为1个a，2个b。由于已占用字符集不能再使用，因此，
 * 剩余可用字符为2个a，3个b，2个c。因此输出a:2,b:3,c:2。注意，输出的字符顺序要跟输入一致。 不能输出b:3,a:2,c:2。如果某个字符已全被占用，不需要输出。例如a:3,b:5,c:2@a:3,b:2，输出为b:3,c:2。
 * 输出描述： 可用字符集。输出带回车换行。
 * <p>
 * 示例1： 输入：a:3,b:5,c:2@a:1,b:2
 * <p>
 * 输出：a:2,b:3,c:2
 * <p>
 * 可以利用map？？？
 */
public class FindUnusedCharacters {

  public static void main(String[] args) {

    String s = "a:3,b:5,c:2@";
    String ss = find(s);
    System.err.println(ss);

  }

  private static String find(String s) {
    int atIndex = s.indexOf('@');
    if (atIndex == s.length() - 1) {
      return s.substring(0, atIndex);
    }

    Map<String, Integer> usedMap = new HashMap<>();
    String[] usedArr = s.substring(atIndex + 1).split(",");
    for (String used : usedArr) {

      usedMap.put(c(used), i(used));
    }

    //保证顺序
    StringBuilder sb = new StringBuilder();
    String[] allArr = s.substring(0, atIndex).split(",");
    for (String all : allArr) {
      int count = i(all);
      String allStr = c(all);
      int diff = count;
      Integer usedCount = usedMap.get(allStr);
      if (usedCount != null) {
        diff = count - usedCount;
      }
      if (diff > 0) {
        sb.append(allStr).append(":").append(diff).append(",");
      }
    }

    return sb.deleteCharAt(sb.length() - 1).toString();
  }

  private static String c(String s) {
    return s.substring(0, s.indexOf(':'));
  }

  private static int i(String s) {
    return Integer.parseInt(s.substring(s.indexOf(':') + 1));
  }
}
