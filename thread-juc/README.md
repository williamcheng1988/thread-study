Thread JUC
===============
JUC 学习

#### [测试:计算数组元素之和](thread-juc/src/test/java/com/william/thread/arraysum/SumTest.java)
    实现方案：
    1. 单线程循环相加
    2. thread-pool ：按照元素个数，new出响应个数的线程
    3. thread-pool ：分治方式
        问题：存在线程数量可能耗尽、并相互等待的情况，导致整个线程池不可用
        解决方案：
            a. 使用 thread pool 方案：
                1. newFixedThreadPool 指定 线程数量 = 2*(数组元素个数/单个线程处理元素最大个数 + (数组元素个数%单个线程处理元素最大个数>0)?1:0)-1
                2. newCachedThreadPool  当需要线程时，自动创建
            c. 使用 fork-join 框架，并行计算


#### [测试:thread-pool 计算数组元素之和>单线程 vs FJ](thread-juc/src/test/java/com/william/thread/arraysum/SumBenchmark.java)
   
#####---------------------------------------------------------------------------------------------------------------------------------
#### [测试:RecursiveAction>数组排序](thread-juc/src/test/java/com/william/thread/forkJoin/RecursiveActionTest.java)
操作步骤：

    1. 一个无序数组拆分、排序
    2. 多个有序数组：归并排序

#### [测试:RecursiveAction>单线程 vs FJ](thread-juc/src/test/java/com/william/thread/forkJoin/RecursiveActionBenchmark.java)
#####---------------------------------------------------------------------------------------------------------------------------------
#### [测试:RecursiveTask>](thread-juc/src/test/java/com/william/thread/forkJoin/RecursiveTaskTest.java)
操作步骤：

    1. 一个无序数组拆分、排序
    2. 多个有序数组：归并排序
    
#### [测试:RecursiveTask>单线程 vs FJ](thread-juc/src/test/java/com/william/thread/forkJoin/RecursiveTaskBenchmark.java)
#####---------------------------------------------------------------------------------------------------------------------------------
#### [测试:CountedCompleter](thread-juc/src/test/java/com/william/thread/forkJoin/CountedCompleterTest.java)
#### [测试:ForkJoinPool.ManagedBlocker>菲波拉契数计算](thread-juc/src/test/java/com/william/thread/forkJoin/ManagedBlockerTest.java)
    测试功能：fork-join ManagedBlocker 功能，對FJ功能的完善和補充
        当程序中需要阻塞线程的时候，调用block()，FJ 新增线程利用多核进行处理
        当程序处理完成以后，调用 	isReleasable()
    测试案例：菲波拉契数计算

