package com.learn.algorithm.sum;

import com.learn.algorithm.util.RandomUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/12 14:08
 */
public class SumFinderTest {

    public static void main(String[] args) {
        SumFinder finder=new IteratorSumFinder();


        int[] source= RandomUtils.randomArrayUnique(100,-1000,1000);
        int sum=0;
        List<Unit> units=finder.threeSum(source,sum);
        if (units!=null && units.size()>0){
            units.forEach(unit -> System.err.println(Arrays.toString(unit.getElements())));
        }
    }
}
