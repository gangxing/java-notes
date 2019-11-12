package com.learn.algorithm.sum;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/12 14:06
 */
@Getter
@Setter
public class Unit {

    private int[] elements;


    public Unit(int... i) {
        this.elements = i;
    }

    public boolean valid(int sum) {
        for (int e : elements) {
            sum -= e;
        }
        return sum == 0;
    }

    @Override
    public String toString() {
        return "Unit" + Arrays.toString(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit unit = (Unit) o;
        return Arrays.equals(getElements(), unit.getElements());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getElements());
    }



}
