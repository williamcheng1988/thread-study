package com.william.thread;

import java.util.concurrent.*;

public class ThreadPoolUtilTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 初始化线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //2. 线程池 监视器
        ThreadPoolMonitor threadPoolMonitor = new ThreadPoolMonitor((ThreadPoolExecutor) executorService, 2);
        //3. 启动线程池
        new Thread(threadPoolMonitor).start();

        //4. 添加线程任务 1
        Future<String> future = executorService.submit(() -> "我的多线程返回结果测试");
        System.out.println("任务1结果：" + future.get());

        //5. 添加线程任务 2
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("任务2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //6. 关闭任务
        //executorService.shutdown();
        threadPoolMonitor.shutdown();
    }
}
