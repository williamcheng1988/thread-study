package com.william.thread.forkJoin;


import com.william.thread.forkJoin.recursiveaction.MergeSortForkJoinAction;
import com.william.thread.forkJoin.recursiveaction.MergeSortSingleThread;
import com.william.thread.utils.Utils;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;


/**
 * JMH 性能测试
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class RecursiveActionBenchmark {

    @Param({"20000000"})
    public int N;

    @Param({"4", "10", "100"})
    public int M;

    @Param({"100"})
    public int T;

    private int[] arrayToSort;
    private int[] arrayToSortSingleThread;
    private ForkJoinPool pool;

    @Setup(Level.Iteration)
    public void init() {
        arrayToSort = Utils.buildRandomIntArray(N);
        arrayToSortSingleThread = Arrays.copyOf(arrayToSort, arrayToSort.length);

        pool = new ForkJoinPool(M);
    }

    @TearDown(Level.Iteration)
    public void destroy() {
        pool.shutdown();
    }

    @Benchmark
    public int[] forkJoinTasks() {
        MergeSortForkJoinAction mergeSortAction = new MergeSortForkJoinAction(arrayToSort, 100);
        pool.invoke(mergeSortAction);
        return mergeSortAction.getSortedArray();
    }

    @Benchmark
    public int[] singleThreadTask() {
        return MergeSortSingleThread.sequentialSort(arrayToSortSingleThread, 100);
     }

}