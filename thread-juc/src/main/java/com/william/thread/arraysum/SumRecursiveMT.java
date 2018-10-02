package com.william.thread.arraysum;

import com.turingschool.forkjoin.demo.utils.Utils;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 利用 thread pool 计算数组元素之和
 * 存在线程数量可能耗尽、并相互等待的情况，导致整个线程池不可用
 * 解决方案：
 *  a. 使用 thread pool 方案：
 *      1. newFixedThreadPool 指定 线程数量 = 2*(数组元素个数/单个线程处理元素最大个数 + (数组元素个数%单个线程处理元素最大个数>0)?1:0)-1
 *      2. newCachedThreadPool  当需要线程时，自动创建
 *  b. 使用 fork-join 框架
 */
public class SumRecursiveMT {
    public static class RecursiveSumTask implements Callable<Long> {
        public static final int SEQUENTIAL_CUTOFF = 1;
        int lo;
        int hi;
        int[] arr; // arguments
        ExecutorService executorService;

        RecursiveSumTask( ExecutorService executorService, int[] a, int l, int h) {
            this.executorService = executorService;
            this.arr = a;
            this.lo = l;
            this.hi = h;
        }

        public Long call() throws Exception { // override
            System.out.format("%s range [%d-%d] begin to compute %n",
                    Thread.currentThread().getName(), lo, hi);
            long result = 0;
            if (hi - lo <= SEQUENTIAL_CUTOFF) {
                for (int i = lo; i < hi; i++)
                    result += arr[i];

                System.out.format("%s range [%d-%d] begin to finished %n",
                        Thread.currentThread().getName(), lo, hi);
            }
            else {
                RecursiveSumTask left = new RecursiveSumTask(executorService, arr, lo, (hi + lo) / 2);
                RecursiveSumTask right = new RecursiveSumTask(executorService, arr, (hi + lo) / 2, hi);
                Future<Long> lr = executorService.submit(left);
                Future<Long> rr = executorService.submit(right);

                result = lr.get() + rr.get();
                System.out.format("%s range [%d-%d] finished to compute %n",
                        Thread.currentThread().getName(), lo, hi);
            }
            return result;
        }
    }


    public static long sum(int[] arr) throws Exception {
        /**
         * 此处线程数量，必须是  2*n-1
         * 线程数量 = 2*(数组元素个数/单个线程处理元素最大个数 + (数组元素个数%单个线程处理元素最大个数>0)?1:0)-1
         */
        int nofProcessors = Runtime.getRuntime().availableProcessors();
        nofProcessors = 2 * ((arr.length / 1) + (arr.length % 1 > 0 ? 1 : 0)) - 1;
        ExecutorService executorService = Executors.newFixedThreadPool(nofProcessors);
//        ExecutorService executorService = Executors.newCachedThreadPool();

        RecursiveSumTask task = new RecursiveSumTask(executorService, arr, 0, arr.length);
        long result =  executorService.submit(task).get();
        executorService.shutdown();
        System.out.println("元素个数：" + arr.length);
        System.out.println("线程个数：" + nofProcessors);
        return result;
    }
}