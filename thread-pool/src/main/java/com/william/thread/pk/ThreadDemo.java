package com.william.thread.pk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 实例化多个 线程 ，对list添加数据
 */
public class ThreadDemo {

    public void test() throws InterruptedException {
        Long start = System.currentTimeMillis();
        final List<Integer> l = new ArrayList<Integer>();
        final Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread() {
                public void run() {
                    l.add(random.nextInt());
                }
            };
            thread.start();
            thread.join();
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        System.out.println("数据量：" + l.size());
    }

}
