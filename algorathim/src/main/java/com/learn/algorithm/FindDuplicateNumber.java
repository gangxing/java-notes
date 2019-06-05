package com.learn.algorithm;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName FindDuplicateNumber
 * @Description 返回数组中唯一重复的数字 要求 算法复杂度O(n) 不适用额外存储空间
 * @Author xgangzai@gmail.com
 * @Date 2019/3/24 18:50
 */
public class FindDuplicateNumber {

    private static int[] arr;

    static int count = 0;

    public static void main(String[] args) {
        FindDuplicateNumber finder = new FindDuplicateNumber();
        finder.init(10);
        System.err.println(count + "--" + Arrays.toString(arr));
        int num = finder.find2();
        System.err.println(count + "-->" + num);
        System.err.println(count + "--" + Arrays.toString(arr));
    }

    //遍历每个元素 如果
    private int find() {

        int t;
        for (int i = 0; i < arr.length; i++) {

            count++;

            while (i + 1 != arr[i]) {
                count++;
                //把这个数丢到它正确的位置上去 把那个数拿回来  如果在丢的过程中 别人已经是正确位置了 说明这个数重复了
                t = arr[arr[i] - 1];
                if (arr[arr[i] - 1] == arr[i]) {
                    return arr[i];
                }
                arr[arr[i] - 1] = arr[i];
                arr[i] = t;
//                System.err.println(count+"--"+Arrays.toString(arr));


            }
        }


        return -1;
    }

    private int find1() {
        for (int i = 1; i < arr.length; i++) {
//            arr[0] = arr[0] ^ i ^ arr[i];//真他么神器
            arr[0] ^= i;
            System.err.println(arr[0]);
            arr[0] ^= arr[i];
            System.err.println(arr[0]);
            System.err.println("------------");
        }
        return arr[0];
    }

    private int find2(){
        int res=arr[0];
        for (int i=1;i<arr.length;i++){
            res^=i;
            res^=arr[i];
        }
        return res;
    }


    private FindDuplicateNumber init(int max) {
        arr = new int[max + 1];
        Random random = new Random();
        for (int n = 1; n < max + 1; n++) {
            while (true) {
                int index = random.nextInt(max + 1);
                if (arr[index] == 0) {
                    arr[index] = n;
                    break;
                }
            }
        }

        System.err.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = random.nextInt(max);
            }
        }
//        arr[0] = 1;
//        arr[1] =3;
//        arr[2] =4;
//        arr[3] = 2;
//        arr[4] = 1;
        return this;
    }
}
