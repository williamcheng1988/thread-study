package com.william.thread.forkJoin;

import com.turingschool.forkjoin.demo.utils.Utils;
import com.william.thread.forkJoin.recursiveaction.MergeSortForkJoinAction;
import com.william.thread.forkJoin.recursiveaction.MergeSortSingleThread;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertArrayEquals;

/**
 * User: William Cheng
 * Create Time: 2018/10/2 11:20
 * Description:
 *  fork-join  RecursiveAction 无返回值的的使用案例
 *  示例：归并排序算法（原理:两个有序的数组，归并为一个有序的数组，从头对应位置的比较）
 */
public class RecursiveActionTest {

    @Test
    public void test() {
        int[] arrayToSortSingleThread = Utils.buildRandomIntArray(20000000);
        int[] arrayToSortMultiThread = Arrays.copyOf(arrayToSortSingleThread, arrayToSortSingleThread.length);

        // 单线程测试：SINGLE THREADED
        MergeSortSingleThread shortestPathServiceSeq = new MergeSortSingleThread(arrayToSortSingleThread, 100);
        int[] sortSingleThreadArray = shortestPathServiceSeq.sequentialSort();

        // fork-join多线程测试：MULTI THREADED
        int nofProcessors =  Runtime.getRuntime().availableProcessors();
        MergeSortForkJoinAction mergeSortAction = new MergeSortForkJoinAction(arrayToSortMultiThread, nofProcessors);

        ForkJoinPool forkJoinPool = new ForkJoinPool(nofProcessors);
        forkJoinPool.invoke(mergeSortAction);

        forkJoinPool.shutdown();

        assertArrayEquals(sortSingleThreadArray, mergeSortAction.getSortedArray());

    }
}