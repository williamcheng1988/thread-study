package com.william.thread;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 11:08
 * Description:
 */

public class CallableDemoTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        CallableDemo demo = new CallableDemo();

        FutureTask task = new FutureTask<>(demo);
        new Thread(task).start();
        System.out.println("JUnit test get result:" + task.get());
    }
}