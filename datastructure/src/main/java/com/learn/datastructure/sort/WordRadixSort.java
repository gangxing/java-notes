package com.learn.datastructure.sort;

import java.util.Arrays;

/**
 * @Description 利用基数排序思想排序单词
 * @Author xgangzai
 * @Date 2019/8/14 23:46
 */
public class WordRadixSort {

  private static final char BLANK = ' ';
  private static final char DEFAULT_CHAR = '\u0000';
  //    static char[] buketChars = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
  private static int bucketCount = 1;
  private static char[] buketChars = new char[bucketCount];

  public static void main(String[] args) {
    String source = "English Language Day was first celebrated in alongside Arabic Language Day Chinese Language Day French Language Day Russian Language Day and Spanish Language Day These are the six official languages of the United Nations and each has a special day designed to raise awareness of the history culture and achievements of these languages. An ancient Hebraic text says:\" love is as strong as death\". It seems that not everyone experiences this kind of strong love. The increasing probably,crime and war tells us that the world is in indispensable need of true love. But what is true love? Love is something we all need. But how do we know when we experience it? True love is best seen as the promotion and action, not an emotion. Love is not exclusively based how we feel. Certainly our emotions are involved. But they cannot be our only criteria for love. True love is when you care enough about another person that you will lay down your life for them. When this happens,then love truly is as strong as death. How many of you have a mother, or father,husband or wife,son or daughter or friend who would sacrifice his or her own life on yours? Those of you who truly love your spells but unchildren, would unselfishly lay your life on the line to save them from death? Many people in an emergency room with their loved ones and prayed\"please, God, take me instead of them\". Find true love and be a true lover as well. May you find a love which is not only strong as death, but to leave to a truly for feeling life.";
//        String source = "we of me a An an";
    String[] words = source.split("\\s");
    String[] words1 = new String[words.length];
    System.arraycopy(words, 0, words1, 0, words.length);
    sort(words);
    Arrays.sort(words1);
    System.err.println("words1 " + Arrays.toString(words1));
    for (int i = 0; i < words.length; i++) {
      if (!words1[i].equals(words[i])) {
        System.err.println("words1 " + words1[i] + " not equals");
        break;
      }
    }
  }

  public static void sort(String[] words) {

    int maxLength = 0;
    for (String word : words) {
      if (word.length() > maxLength) {
        maxLength = word.length();
      }
      //初始化桶
      adjustBuckets(word);

    }

    checkBlank();

    System.err.println(new String(buketChars));

    System.err.println("origin " + Arrays.toString(words));

    int[] count = new int[bucketCount];
    //每个字符一个桶 ' ', 'A', 'B', 'C', 'D', ... 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', ... 'x', 'y', 'z'
    String[][] buckets = new String[bucketCount][words.length];
    for (int round = 0; round < maxLength; round++) {//从前往后比较
      for (String s : words) {
        int index = maxLength - 1 - round;
        char c = BLANK;
        if (s.length() > index) {
          c = s.charAt(index);
        }
        //已经到最后一位了 直接放入' '桶中
        int bucketIndex = bucketIndex(c);
        buckets[bucketIndex][count[bucketIndex]++] = s;
      }

      //回填到原数组中
      int bucketIndexNew = 0;
      for (int bucketIndex = 0; bucketIndex < bucketCount; bucketIndex++) {
        String[] bucket = buckets[bucketIndex];
        for (int i = 0; i < count[bucketIndex]; i++) {
          words[bucketIndexNew++] = bucket[i];
        }
        count[bucketIndex] = 0;
      }
    }

    System.err.println("sorted " + Arrays.toString(words));
  }

  private static void adjustBuckets(String word) {

    char[] chars = word.toCharArray();
    for (char c : chars) {

      boolean grow = true;
      for (int i = 0; i < bucketCount; i++) {
        char bucketChar = buketChars[i];
        if (bucketChar == c) {//已经有了 忽略
          grow = false;
          break;
        }

        if (bucketChar == DEFAULT_CHAR) {
          grow = false;
          buketChars[i] = c;// 利用插入排序
          for (int ii = 0; ii < i; ii++) {
            if (c < buketChars[ii]) {
              //整体向后移一位
              for (int iii = i; iii > ii; iii--) {
                buketChars[iii] = buketChars[iii - 1];
              }
              buketChars[ii] = c;
              break;
            }
          }
        }
      }
      if (grow) {
        bucketCount++;
        char[] bucketCharsNew = new char[bucketCount];
        System.arraycopy(buketChars, 0, bucketCharsNew, 0, buketChars.length);
        buketChars = bucketCharsNew;

        //递归
        adjustBuckets(word);
      }
    }

  }

  private static void checkBlank() {
    //校验' '是否存在
    boolean hasBlank = false;
    for (char c : buketChars) {
      if (c == BLANK) {//空白字符照理来说应该在第一个 因为它是可见字符中最小的一个
        hasBlank = true;
        break;
      }
    }
    if (!hasBlank) {
      bucketCount++;
      char[] bucketCharsNew = new char[bucketCount];
      System.arraycopy(buketChars, 0, bucketCharsNew, 1, buketChars.length);
      bucketCharsNew[0] = BLANK;
      buketChars = bucketCharsNew;
    }
  }


  private static int bucketIndex(char c) {
    for (int i = 0; i < buketChars.length; i++) {
      if (buketChars[i] == c) {
        return i;
      }
    }
    throw new IllegalArgumentException(c + "不存在");
  }
}
