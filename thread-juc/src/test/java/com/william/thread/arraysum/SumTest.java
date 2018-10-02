package com.william.thread.arraysum;

import com.turingschool.forkjoin.demo.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: William Cheng
 * Create Time: 2018/10/2 19:26
 * Description:
 */
public class SumTest {

    static int NUM = 1000;

    public static void main(String[] args) throws Exception {
        int[] arr = Utils.buildRandomIntArray(2000);
        System.out.printf("The array length is: %d\n", arr.length);

        /*
         * 1. 单线程进行计算
         */
        long result1 = SumSequential.sum(arr);

        /*
         * 2. 多线程进行计算(按照元素个数计算得出所有线程)
         */
        SumMultiThreads sumMultiThreads = new SumMultiThreads(NUM);
        int numThreads = arr.length / NUM > 0 ? arr.length / NUM : 1;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        long result2 = sumMultiThreads.sum(arr, executor);
        executor.shutdown();
        System.out.printf("The result2 is: %d\n", result2);

        /*
         * 3. 分治方式 计算
         */
        long result3 = SumRecursiveMT.sum(arr);
        System.out.printf("The result3 is: %d\n", result3);
    }
}