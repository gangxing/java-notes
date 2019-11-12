package com.learn.algorithm.sum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/12 14:11
 */
public class IteratorSumFinder implements SumFinder {

    @Override
    public List<Unit> twoSum(int[] source, int sum) {
        return twoSum1(source, sum);
    }

    private List<Unit> twoSum0(int[] source, int sum) {
        List<Unit> list = new ArrayList<>();
        //两重循环
        if (source.length < 2) {
            return list;
        }

        for (int i = 0; i < source.length - 1; i++) {
            for (int j = i + 1; j < source.length; j++) {
                Unit unit = new Unit(source[i], source[j]);
                System.err.println(unit);
                if (unit.valid(sum)) {
                    list.add(unit);
                }
            }
        }

        return list;
    }

    private List<Unit> twoSum1(int[] source, int sum) {
        List<Unit> list = new ArrayList<>();
        Map<Integer, Integer> value2IndexMap = new HashMap<>(source.length);
        for (int i = 0; i < source.length; i++) {
            //这样如果有重复的怎么办
            value2IndexMap.put(source[i], i);
        }

        //这样输出每一对会输出两次，看来是重复了 只需要遍历一半吗
        for (int i = 0; i < source.length - 1; i++) {
            int left = sum - source[i];
            Integer anotherIndex = value2IndexMap.get(left);

            if (anotherIndex != null) {
                Unit unit = new Unit(i, anotherIndex);
                list.add(unit);
            }
        }
        return list;
    }

    @Override
    public List<Unit> threeSum(int[] source, int sum) {
        return threeSum1(source, sum);
    }


    private List<Unit> threeSum0(int[] source, int sum) {
        List<Unit> list = new ArrayList<>();
        //三重循环
        if (source.length < 3) {
            return list;
        }

        for (int i = 0; i < source.length - 2; i++) {
            for (int j = i + 1; j < source.length - 1; j++) {
                for (int k = j + 1; k < source.length; k++) {
                    Unit unit = new Unit(source[i], source[j], source[k]);

                    System.err.println(unit);
                    if (unit.valid(sum)) {
                        list.add(unit);
                    }
                }
            }
        }

        return list;
    }


    private List<Unit> threeSum1(int[] source, int sum) {
        List<Unit> list = new ArrayList<>();
        //三重循环
        if (source.length < 3) {
            return list;
        }

        Map<Integer, Integer> value2IndexMap = new HashMap<>(source.length);
        for (int i = 0; i < source.length; i++) {
            //这样如果有重复的怎么办
            value2IndexMap.put(source[i], i);
        }


        for (int i = 0; i < source.length; i++) {
            for (int j = i + 1; j < source.length - 1; j++) {
                int left = sum - source[i] - source[j];

                Integer anotherIndex = value2IndexMap.get(left);

                if (anotherIndex != null) {
                    Unit unit = new Unit(source[i], source[j], left);
                    list.add(unit);
                }
            }
        }

        return list;
    }
}
