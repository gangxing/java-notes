package com.learn.algorithm;

/**
 * @ClassName FindTwoAddens
 * @Description FIXME 去网上找实现思路
 * @Author xgangzai@gmail.com
 * @Date 2019/7/11 09:26
 */
public class FindTwoAddens {
    /**
     * 给定一个整数数组（假定都是正整数）和一个目标值，在数组中找出两个元素，他们和为目标值。答案唯一
     * 要求：元素不能被重复访问
     */


    /**
     * @param source
     * @param sum
     * @return 两个加数的下标，如果没找到返回[-1,-1]
     */
    public int[] find(int[] source, int sum) {
        //两头往中间找？不行，数组可没保证是有序的
        //两个元素肯定都比和要小
        //先用最笨的方法实现两层遍历 O(N^2)
        int firstIndex = -1;
        int secondIndex = -1;
        out:
        for (int i = 0; i < source.length; i++) {
            for (int j = i + 1; j < source.length; j++) {
                if (source[i] + source[j] == sum) {
                    firstIndex=i;
                    secondIndex=j;
                    break out;
                }
            }
        }


        return new int[]{firstIndex, secondIndex};
    }


    public static void main(String[] args) {
        FindTwoAddens finder = new FindTwoAddens();
        int[] source = {2, 7, 11, 15};
        int sum = 9;
        int[] resultIndexs = finder.find(source, sum);
        if (resultIndexs[0] != -1 && resultIndexs[1] != -1) {
            int calculated = source[resultIndexs[0]] + source[resultIndexs[1]];
            boolean right = calculated == sum;
            System.err.println(right ? "right" : "wrong");
        }else {

            System.err.println("not found");
        }
    }

}
