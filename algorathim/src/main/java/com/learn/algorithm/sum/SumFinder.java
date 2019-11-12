package com.learn.algorithm.sum;

import java.util.List;

/**
 * @Description 核心思想：将最后一个未知数先算出来，然后通过hash 表直接去查有没有，而不用再从头遍历一遍，逐个比对
 * 将遍历n个比对操作换成hash表一次定位操作。增加O(N)的空间复杂度，降低一个数量级的时间复杂度O(N^m)->O(N^(m-1))
 * 其中m是求m个数之和
 * @Author xgangzai
 * @Date 2019/11/12 14:06
 */
public interface SumFinder {

    /**
     * 找出2个数和为sum，返回下标
     * 要求时间复杂度O(n) 或者O(nlgn)
     * 空间复杂度可以为O(n)
     * 首先从能实现的角度来讲，直接上穷举，两层遍历，时间复杂度为O(n^2)
     * 典型地要用空间换时间，
     *
     * @param source
     * @param sum
     * @return
     */
    List<Unit> twoSum(int[] source, int sum);

    /**
     * 找出三个数和为sum
     * 按照暴力穷举需要时间复杂度O(n^3)
     * a + b + c = sum 可以转化成 a + b = sum - c,变成求n 次 twoSum操作，
     * 所以时间复杂度可以降为O(n^2)，但是需要O(n)的空间复杂度
     *
     * @param source
     * @param sum
     * @return
     */
    List<Unit> threeSum(int[] source, int sum);
}
