package com.william.thread.forkJoin.recursivetask;

import com.william.thread.utils.CalcUtil;

import java.util.concurrent.RecursiveTask;

/**
 * The class first sums an array sequentially then sums the array using the F/J framework.
 * This proves that for < 100 computational steps, sequential is better.
 * <p>
 * To prove that for > 100 computational steps, F/J is better, change boolean: extraWork = true;
 * 计算数组元素之和
 */
public class LongSumTask extends RecursiveTask<Long> {

    static final int SEQUENTIAL_THRESHOLD = 1000;
    static final long NPS = (1000L * 1000 * 1000);
    static final boolean extraWork = true; // change to add more than just a sum


    int low;
    int high;
    int[] array;

    public LongSumTask(int[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    }

    protected Long compute() {

        if (high - low <= SEQUENTIAL_THRESHOLD) {
            return CalcUtil.calSum(array);
        } else {
            int mid = low + (high - low) / 2;
            LongSumTask left = new LongSumTask(array, low, mid);
            LongSumTask right = new LongSumTask(array, mid, high);
            left.fork();
            long rightAns = right.compute();
            long leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}

       