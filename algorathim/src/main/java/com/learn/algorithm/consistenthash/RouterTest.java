package com.learn.algorithm.consistenthash;

import com.learn.algorithm.util.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/22 16:24
 */
public class RouterTest {

    public static void main(String[] args) {
       AbstractHashRouter router =new ConsistentHashRouter(120);
       String nodeNamePrefix="Node-";
       for (int i=0;i<3;i++){
           router.addNode(nodeNamePrefix+i);
       }

       for (int i=0;i<1000;i++){
           String s=Data.random();
           router.put(s,s);
       }


        for (int i=0;i<500;i++){
            String s=Data.random();
            if (i==230){
                router.removeNode(nodeNamePrefix+1);
            }
            router.get(s);
        }

        router.print();
    }

    private static class Data{
        static int capacity=1024;
        static List<String> s=new ArrayList<>(capacity);

        static {
            for (int i=0;i<capacity;i++){
                s.add(RandomStringUtils.random(5));
            }
        }

        static String random(){
            Random random=new Random();
            return s.get(random.nextInt(capacity));
        }

    }
}
