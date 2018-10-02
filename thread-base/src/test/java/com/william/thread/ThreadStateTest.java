package com.william.thread;

import org.junit.Test;

/**
 * User: William Cheng
 * Create Time: 2018/9/15 11:08
 * Description:
 */

public class ThreadStateTest {

    public static void main(String[] args) {
        new Thread(new ThreadStateDemo.TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new ThreadStateDemo.Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new ThreadStateDemo.Blocked(), "BlockedThread-1").start();
        new Thread(new ThreadStateDemo.Blocked(), "BlockedThread-2").start();
    }
}