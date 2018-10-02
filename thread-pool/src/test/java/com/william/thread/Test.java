package com.william.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 16:34
 * Description:
 */
public class Test {

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(0);
        ExecutorService executorService = new ThreadPoolExecutor(
                0, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(10),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("拒绝。。。");
                    }
                });

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("test ..." + integer.incrementAndGet());
                }
            });
        }
    }

}