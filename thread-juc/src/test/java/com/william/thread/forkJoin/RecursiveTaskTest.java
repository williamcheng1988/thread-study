package com.william.thread.forkJoin;

import com.william.thread.forkJoin.recursivetask.LongSumMain;
import com.william.thread.forkJoin.recursivetask.LongSumTask;
import com.william.thread.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * User: William Cheng
 * Create Time: 2018/10/2 11:20
 * Description:
 */
public class RecursiveTaskTest {


    @Test
    public void test() throws ExecutionException, InterruptedException {
        int[] array = Utils.buildRandomIntArray(20000000);


        /*
         * 单线程计算
         */
        LongSumMain longSumMain = new LongSumMain();
        long longSum1 = longSumMain.seqSum(array);
        System.out.println("single-thread sum=" + longSum1);

        /*
         * 多线程：fork-join RecursiveTask 计算
         */
        LongSumTask ls = new LongSumTask(array, 0, array.length);
        ForkJoinPool fjp  = new ForkJoinPool(4); // with number of threads to use
        ForkJoinTask<Long> result = fjp.submit(ls);
        long longSum2 = result.get();
        fjp.shutdown();
        System.out.println("fork-join sum=" + longSum2);

        Assert.assertEquals(longSum1, longSum2);
    }


}