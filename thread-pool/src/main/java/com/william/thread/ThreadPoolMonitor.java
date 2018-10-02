package com.william.thread;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池 监视器
 */
public class ThreadPoolMonitor implements Runnable {
    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run = true;

    public ThreadPoolMonitor(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
    }

    public void shutdown() {
        this.run = false;
        System.out.println("关闭线程池");
        executor.shutdown();
    }

    @Override
    public void run() {
        while (run) {
            if (this.executor.isTerminated()) {
                System.out.println("任务执行完成");
                break;
            }
            System.out.println(
                    String.format("[monitor] 池大小:%d,核心数：%d, 活跃数: %d, 完成数: %d, 任务数: %d, 线程结束没: %s, 任务结束没: %s",
                            this.executor.getPoolSize(),
                            this.executor.getCorePoolSize(),
                            this.executor.getActiveCount(),
                            this.executor.getCompletedTaskCount(),
                            this.executor.getTaskCount(),
                            this.executor.isShutdown(),
                            this.executor.isTerminated()));
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}