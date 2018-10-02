package com.william.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulePoolDemo {

    //计数器
    private AtomicInteger integer = new AtomicInteger(1);
    //线程池调度器
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    public void submit() {
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("直接执行:" + integer.get());
            }
        });
    }

    public void schedule() {

        scheduledExecutorService.schedule(() -> {
            System.out.println("5s后执行:" + integer.get());
        }, 5, TimeUnit.SECONDS);

    }

    public void scheduleWithFixedDelay(int delay) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("每次延迟时间" + delay + " 执行(无需等待上次任务执行完成):" + integer.get());
            }
        }, 0, delay, TimeUnit.SECONDS);
    }


    public void scheduleAtFixedRate(int period) {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("固定频率"+period+" 执行(需要等待上次任务执行完成):" + integer.addAndGet(1));
                //throw new RuntimeException(); //有异常了就不会在执行了
            }
        }, 0, period, TimeUnit.SECONDS);
    }

}