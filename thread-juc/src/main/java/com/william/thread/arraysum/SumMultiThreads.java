package com.william.thread.arraysum;

import com.turingschool.forkjoin.demo.utils.SumUtils;
import com.turingschool.forkjoin.demo.utils.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多线程计算数组之和
 */
public class SumMultiThreads {
    public SumMultiThreads() {
    }

    public SumMultiThreads(int num) {
        NUM = num;
    }

    static int NUM = 0;

    public static long sum(int[] arr, ExecutorService executor) throws Exception {
        long result = 0;
        int numThreads = arr.length / NUM > 0 ? arr.length / NUM : 1;

        SumTask[] tasks = new SumTask[numThreads];
        Future<Long>[] sums = new Future[numThreads];
        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new SumTask(arr, (i * NUM), ((i + 1) * NUM));
            sums[i] = executor.submit(tasks[i]);
        }

        for (int i = 0; i < numThreads; i++) {
            result += sums[i].get();
        }
        return result;
    }
}
class SumTask implements Callable<Long> {
    int lo;
    int hi;
    int[] arr;

    public SumTask(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    public Long call() { //override must have this type
        //System.out.printf("The range is [%d - %d]\n", lo, hi);
        long result = SumUtils.sumRange(arr, lo, hi);
        return result;
    }
}