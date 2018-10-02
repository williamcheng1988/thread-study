package com.william.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 15:42
 * Description: 线程并发测试    ！！！！！暂未解决
 */
public class SyncTest2 {

    //线程池调度器
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    ReentrantLock lock = new ReentrantLock();

    public void test() {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (lock.tryLock()) {
                    System.out.println("执行正常");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    lock.unlock();
                } else {
                    System.out.println("同步执行异常");
                    scheduledExecutorService.shutdown();
                    throw new RuntimeException();
                }
            }
        }, 0, 1, TimeUnit.MICROSECONDS);
    }


    public static void main(String[] args) {
        SyncTest2 test = new SyncTest2();
        test.test();
    }

}