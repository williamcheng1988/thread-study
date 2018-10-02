package com.william.thread.pk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1 个线程池，单个线程，对list添加数据
 */
public class ThreadPoolDemo {

    public void test() throws InterruptedException {
        Long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<Integer>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    list.add(j);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        System.out.println("数据量：" + list.size());
    }

}
